package com.Portfolio.Handy.api;

import com.Portfolio.Handy.dto.ShortCutDto;
import com.Portfolio.Handy.dto.ShortCutGroupDto;
import com.Portfolio.Handy.service.ShortCutGroupService;
import com.Portfolio.Handy.service.ShortCutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupApiController {
    @Autowired
    private final ShortCutGroupService shortCutGroupService;

    @Autowired
    private final ShortCutService shortCutService;

    //    그룹, 단축키 저장
    @PostMapping("api/shortcut/{Id}/groups")
    public ResponseEntity<ShortCutGroupDto> save(@PathVariable Long Id, @RequestBody ShortCutGroupDto group) {
        ShortCutGroupDto shortCutGroupDto = shortCutGroupService.saveGroup(Id, group);
        ShortCutGroupDto result = shortCutService.saveShortCut(shortCutGroupDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //    즐겨찾기 수정 , PatchMapping 오류
    @PutMapping("api/shortcut/{Id}/groups")
    public void isFavorite(@RequestBody ShortCutGroupDto group) {
        shortCutGroupService.favoriteEdit(group);
    }

    //    그룹 삭제
    @DeleteMapping("api/shortcut/{Id}/groups")
    public ResponseEntity<ShortCutGroupDto> delete(@RequestBody ShortCutGroupDto group) {
        ShortCutGroupDto shortCutGroupDto = shortCutGroupService.deleteGroup(group);
        return ResponseEntity.status(HttpStatus.OK).body(shortCutGroupDto);
    }

    //    단축키 삭제
    @DeleteMapping("api/shortcut/{Id}/shortcut")
    public ResponseEntity<ShortCutGroupDto> deleteShortCut(@RequestBody ShortCutDto shortCutDto) {
        ShortCutGroupDto shortCutGroupDto = shortCutService.deleteShortCut(shortCutDto);
        return ResponseEntity.status(HttpStatus.OK).body(shortCutGroupDto);
    }
}
