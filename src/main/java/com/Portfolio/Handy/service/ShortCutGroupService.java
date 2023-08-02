package com.Portfolio.Handy.service;

import com.Portfolio.Handy.dto.ProgramDto;
import com.Portfolio.Handy.dto.ShortCutGroupDto;
import com.Portfolio.Handy.entity.Program;
import com.Portfolio.Handy.entity.Shortcutgroup;
import com.Portfolio.Handy.repository.ProgramRepository;
import com.Portfolio.Handy.repository.ShortCutGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ShortCutGroupService {
    @Autowired
    private final ShortCutGroupRepository shortCutGroupRepository;

    @Autowired
    private final ShortCutService shortCutService;

    @Autowired
    private final ProgramRepository programRepository;

    // 프로그램별 전체 조회
    public List<ShortCutGroupDto> shortCutGroups(Long programId) {
        List<ShortCutGroupDto> shortCutGroupDtoList =
                shortCutGroupRepository.findByProgramId(programId)
                        .stream()
                        .map(group -> ShortCutGroupDto.createShortCutGroupDto(group))
                        .collect(Collectors.toList());

        return shortCutService.setShortCutAndRemove(shortCutGroupDtoList);
    }

    // 즐겨찾기, 최근 수정순으로 전체 조회
    public List<ShortCutGroupDto> groupsOrderFavorite(Long id) {
        List<ShortCutGroupDto> shortCutGroupDtoList =
                shortCutGroupRepository.orderByFavorite(id)
                        .stream()
                        .map(group -> ShortCutGroupDto.createShortCutGroupDto(group))
                        .collect(Collectors.toList());

        return shortCutService.setShortCutAndRemove(shortCutGroupDtoList);
    }

    //  검색 기능
    public List<ShortCutGroupDto> serachGroup(Long id, String serachText) {
        List<ShortCutGroupDto> shortCutGroupDtoList =
                shortCutGroupRepository.serachByText(id, serachText)
                        .stream()
                        .map(group -> ShortCutGroupDto.createShortCutGroupDto(group))
                        .collect(Collectors.toList());

        return shortCutService.setShortCutAndRemove(shortCutGroupDtoList);
    }

    // 가장 최근에 수정한 6개 그룹 조회
    public List<ShortCutGroupDto> recentModifiedGroup(Long id) {
        List<ShortCutGroupDto> shortCutGroupDtoList = shortCutGroupRepository.recentModifiedGroup(id)
                .stream()
                .map(group -> ShortCutGroupDto.createShortCutGroupDto(group))
                .collect(Collectors.toList());

        return shortCutGroupDtoList;
    }

    public List<String> blankBox(List<ProgramDto> list) {
        List<String> blank = new ArrayList<>();

        for(int i = 0; i < 6 - list.size(); i++) {
            blank.add("blank");
        }

        return blank;
    }

    // 그룹 저장
    public ShortCutGroupDto saveGroup(Long id, ShortCutGroupDto shortCutGroupDto) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("그룹 생성을 실패했습니다."));

        Shortcutgroup target = new Shortcutgroup();

        if (shortCutGroupDto.getId() == null) {
            target = Shortcutgroup.createGroup(shortCutGroupDto, program);
        } else {
            target = shortCutGroupRepository.findById(shortCutGroupDto.getId()).orElse(null);
            target.patch(shortCutGroupDto);
        }

        Shortcutgroup save = shortCutGroupRepository.save(target);
        ShortCutGroupDto returnDto = ShortCutGroupDto.createShortCutGroupDto(save);
        returnDto.setShortCutDtoList(shortCutGroupDto.getShortCutDtoList());

        return returnDto;
    }

    // 즐겨찾기 수정
    public void favoriteEdit(ShortCutGroupDto shortCutGroupDto) {
        Shortcutgroup shortcutgroup = shortCutGroupRepository.findById(shortCutGroupDto.getId())
                .orElseThrow(() -> new IllegalStateException("즐겨찾기 등록을 실패했습니다."));

        shortcutgroup.setIsFavorite(shortCutGroupDto.getIsFavorite());
        shortCutGroupRepository.save(shortcutgroup);
    }

    // 그룹 삭제
    public ShortCutGroupDto deleteGroup(ShortCutGroupDto shortCutGroupDto) {
        Shortcutgroup shortcutgroup = shortCutGroupRepository.findById(shortCutGroupDto.getId())
                .orElseThrow(() -> new IllegalStateException("그룹 삭제를 실패했습니다."));

        shortCutGroupRepository.delete(shortcutgroup);
        return ShortCutGroupDto.createShortCutGroupDto(shortcutgroup);
    }
}
