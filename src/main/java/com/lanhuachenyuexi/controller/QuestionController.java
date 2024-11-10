package com.lanhuachenyuexi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.lanhuachenyuexi.common.BaseResponse;
import com.lanhuachenyuexi.common.ErrorCode;
import com.lanhuachenyuexi.common.ResultUtils;
import com.lanhuachenyuexi.exception.ThrowUtils;
import com.lanhuachenyuexi.model.dto.qustion.QuestionQueryRequest;
import com.lanhuachenyuexi.model.entity.Question;
import com.lanhuachenyuexi.model.vo.QuestionVO;
import com.lanhuachenyuexi.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 题目接口
 *
 * @author <a href="https://github.com/jhaugus">蓝花晨月夕</a>

 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    /**
     * 分页获取题目列表（封装类）
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                               HttpServletRequest request) {
        ThrowUtils.throwIf(questionQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        log.info("********************** listQuestionVOByPage  **********************");
        // 查询数据库
        Page<Question> questionPage = questionService.listQuestionByPage(questionQueryRequest);
        // 获取封装类
        return ResultUtils.success(questionService.getQuestionVOPage(questionPage, request));
    }

}
