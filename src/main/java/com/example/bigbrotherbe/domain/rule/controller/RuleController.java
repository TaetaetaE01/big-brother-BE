package com.example.bigbrotherbe.domain.rule.controller;

import com.example.bigbrotherbe.domain.rule.dto.response.RuleResponse;
import com.example.bigbrotherbe.domain.rule.entity.Rule;
import com.example.bigbrotherbe.domain.rule.service.RuleService;
import com.example.bigbrotherbe.common.exception.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.example.bigbrotherbe.common.constant.Constant.GetContent.PAGE_DEFAULT_VALUE;
import static com.example.bigbrotherbe.common.constant.Constant.GetContent.SIZE_DEFAULT_VALUE;
import static com.example.bigbrotherbe.common.exception.enums.SuccessCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rule")
public class RuleController {

    private final RuleService ruleService;


    @GetMapping("/{ruleId}")
    public ResponseEntity<ApiResponse<RuleResponse>> getRuleById(@PathVariable("ruleId") Long ruleId) {
        RuleResponse ruleResponse = ruleService.getRuleById(ruleId);
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, ruleResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<Rule>>> getRuleList(@RequestParam(name = "affiliation") String affiliation,
                                                               @RequestParam(name = "page", defaultValue = PAGE_DEFAULT_VALUE) int page,
                                                               @RequestParam(name = "size", defaultValue = SIZE_DEFAULT_VALUE) int size,
                                                               @RequestParam(name = "search", required = false) String search) {
        Page<Rule> rulePage;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        if (search != null && !search.isEmpty()) {
            rulePage = ruleService.searchRules(affiliation, search, pageable);
        } else {
            rulePage = ruleService.getRules(affiliation, pageable);
        }
        return ResponseEntity.ok(ApiResponse.success(SUCCESS, rulePage));
    }
}
