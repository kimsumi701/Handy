package com.Portfolio.Handy.service;

import com.Portfolio.Handy.dto.ShortCutDto;
import com.Portfolio.Handy.dto.ShortCutGroupDto;
import com.Portfolio.Handy.entity.Shortcut;
import com.Portfolio.Handy.entity.Shortcutgroup;
import com.Portfolio.Handy.repository.ShortCutGroupRepository;
import com.Portfolio.Handy.repository.ShortCutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShortCutService {
    @Autowired
    private final ShortCutGroupRepository shortCutGroupRepository;

    @Autowired
    private final ShortCutRepository shortCutRepository;

    // 단축키 조회
    public List<ShortCutDto> shorCuts(Long groupId) {
        return shortCutRepository.findByGroupId(groupId)
                .stream()
                .map(shortCut -> ShortCutDto.createShortCutDto(shortCut))
                .collect(Collectors.toList());
    }

    // 그룹별 단축키 조회
    public List<ShortCutGroupDto> setShortCutAndRemove(List<ShortCutGroupDto> shortCutGroupDtoList) {
        // 그룹별 단축키 조회
        shortCutGroupDtoList.forEach(group -> {
            group.setShortCutDtoList(shorCuts(group.getId()));
        });

        // 단축키가 없는 그룹 제외
        List<ShortCutGroupDto> removeList = new ArrayList<>();

        shortCutGroupDtoList.forEach(group -> {
            if (group.getShortCutDtoList().size() == 0) {
                removeList.add(group);
            }
        });

        shortCutGroupDtoList.removeAll(removeList);
        return shortCutGroupDtoList;
    }

    // 단축키 저장
    public ShortCutGroupDto saveShortCut(ShortCutGroupDto shortCutGroupDto) {
        Shortcutgroup shortcutgroup = shortCutGroupRepository.findById(shortCutGroupDto.getId())
                .orElseThrow(() -> new IllegalStateException("단축키 등록을 실패했습니다."));

        List<ShortCutDto> saveList = new ArrayList<>();

        shortCutGroupDto.getShortCutDtoList().forEach(item -> {
            if (item.getId() == null) {
                item.setGroupId(shortCutGroupDto.getId());
                saveList.add(
                        ShortCutDto.createShortCutDto(
                                shortCutRepository.save(Shortcut.createShortCut(item, shortcutgroup))
                        )
                );
            } else {
                Shortcut target = shortCutRepository.findById(item.getId()).orElse(null);
                target.patch(item);
                saveList.add(
                        ShortCutDto.createShortCutDto(
                                shortCutRepository.save(target)
                        )
                );
            }
        });

        ShortCutGroupDto groupDto = shortCutGroupDto;
        groupDto.setShortCutDtoList(saveList);
        return  groupDto;
    }

    //  단축키 삭제
    public ShortCutGroupDto deleteShortCut(ShortCutDto shortCutDto) {
        Shortcut shortcut = shortCutRepository.findById(shortCutDto.getId())
                .orElseThrow(() -> new IllegalStateException("잘못된 접근입니다."));

        Shortcutgroup shortcutgroup = shortCutGroupRepository.findById(shortCutDto.getGroupId()).orElse(null);
        shortCutRepository.delete(shortcut);
        return ShortCutGroupDto.createShortCutGroupDto(shortcutgroup);
    }
}
