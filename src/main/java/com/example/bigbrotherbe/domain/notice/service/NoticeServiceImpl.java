package com.example.bigbrotherbe.domain.notice.service;

import com.example.bigbrotherbe.domain.member.service.MemberService;
import com.example.bigbrotherbe.domain.notice.dto.NoticeModifyRequest;
import com.example.bigbrotherbe.domain.notice.dto.NoticeRegisterRequest;
import com.example.bigbrotherbe.domain.notice.entity.Notice;
import com.example.bigbrotherbe.domain.notice.repository.NoticeRepository;
import com.example.bigbrotherbe.global.exception.BusinessException;
import com.example.bigbrotherbe.global.exception.enums.ErrorCode;
import com.example.bigbrotherbe.global.file.dto.FileSaveDTO;
import com.example.bigbrotherbe.global.file.dto.FileUpdateDTO;
import com.example.bigbrotherbe.global.file.entity.File;
import com.example.bigbrotherbe.global.file.enums.FileType;
import com.example.bigbrotherbe.global.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.bigbrotherbe.global.exception.enums.ErrorCode.NO_EXIST_AFFILIATION;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final FileService fileService;
    private final MemberService memberService;

    @Override
    @Transactional(rollbackFor = Exception.class)   // 트랜잭션 시작 및 커밋 -> 모든 예외상황 발생시 롤백
    public void register(NoticeRegisterRequest noticeRegisterRequest, List<MultipartFile> multipartFiles) {
        if (!memberService.checkExistAffiliationById(noticeRegisterRequest.getAffiliationId())) {
            throw new BusinessException(NO_EXIST_AFFILIATION);
        }

        //        Member member = authUtil.getLoginMember();
        // role에 따라 권한있는지 필터링 없으면 exception

        List<File> files = null;
        if (fileService.checkExistRequestFile(multipartFiles)) {
            FileSaveDTO fileSaveDTO = FileSaveDTO.builder()
                    .fileType(FileType.MEETINGS.getType())
                    .multipartFileList(multipartFiles)
                    .build();

            files = fileService.saveFile(fileSaveDTO);
        }
        Notice notice = noticeRegisterRequest.toNoticeEntity(files);

        if (files != null){
            files.forEach(file -> {
                file.linkNotice(notice);
            });
        }

        noticeRepository.save(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(Long noticeId, NoticeModifyRequest noticeModifyRequest, List<MultipartFile> multipartFiles) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NO_EXIST_NOTICE));

        List<File> files = null;
        if (fileService.checkExistRequestFile(multipartFiles)) {
            FileUpdateDTO fileUpdateDTO = FileUpdateDTO.builder()
                    .fileType(FileType.NOTICE.getType())
                    .multipartFileList(multipartFiles)
                    .files(notice.getFiles())
                    .build();

            files = fileService.updateFile(fileUpdateDTO);
        }

        notice.update(noticeModifyRequest.getTitle(), noticeModifyRequest.getContent(), files);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NO_EXIST_NOTICE));
//        Member member = authUtil.getLoginMember();
//
//        if (participantService.findParticipantInfo(member, notice.getMeeting()).getRole() != Role.HOST) {
//            throw new BusinessException(NOT_HOST_OF_MEETING);
//        }

        noticeRepository.delete(notice);
    }


}