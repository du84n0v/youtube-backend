package com.youtube.service;

import com.youtube.dto.channel.ChannelInfoDTO;
import com.youtube.dto.profile.ProfileInfoDTO;
import com.youtube.dto.report.RequestReportDTO;
import com.youtube.dto.report.RequestReportUpdateDTO;
import com.youtube.dto.report.ResponseReportDTO;
import com.youtube.dto.report.ResponseReportInfoDTO;
import com.youtube.entity.ChannelEntity;
import com.youtube.entity.ProfileEntity;
import com.youtube.entity.ReportEntity;
import com.youtube.entity.VideoEntity;
import com.youtube.enums.ReportTypeEnum;
import com.youtube.exception.AppBadException;
import com.youtube.exception.ItIsNotYourReportException;
import com.youtube.repository.ReportRepository;
import com.youtube.util.PageUtil;
import com.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private AttachService attachService;

    public ResponseReportDTO create(RequestReportDTO requestReportDTO) {
        ProfileEntity profile = profileService.get(SpringSecurityUtil.getCurrentProfileId());
        ReportEntity report = new ReportEntity();
        report.setProfile(profile);
        report.setProfileId(profile.getId());
        report.setContent(requestReportDTO.getContent());
        report.setVideoType(requestReportDTO.getVideoType());
        ChannelEntity channelEntity;
        VideoEntity videoEntity;
        if (requestReportDTO.getVideoType().equals(ReportTypeEnum.VIDEO)) {
            videoEntity = videoService.get(requestReportDTO.getEntityId());
            report.setEntityId(videoEntity.getId());
        } else {
            channelEntity = channelService.get(requestReportDTO.getEntityId());
            report.setEntityId(channelEntity.getId());
        }
        reportRepository.save(report);


        return toResponse(report);
    }
    public Page<ResponseReportInfoDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(PageUtil.page(page), size);
        Page<ReportEntity> entities = reportRepository.pagination(pageable);

        List<ReportEntity> entityList = entities.getContent();
        long totalElement = entities.getTotalElements();

        List<ResponseReportInfoDTO> dtos = new LinkedList<>();
        entityList.forEach(e -> dtos.add(toResponseInfo(e,profileService.toProfileInfoDTO(e.getProfile()))));
        return new PageImpl<>(dtos, pageable, totalElement);

    }
    public String remove(Integer id) {
        Optional<ReportEntity> r=reportRepository.findReportById(id);
        if(r.isEmpty()){
            return "There is no such report with such id";
        }
        reportRepository.deleteById(id);
        return "Successfully removed";
    }
    public List<ResponseReportInfoDTO> getuserReportList(Integer id){
        ProfileEntity profile = profileService.get(id);
        ProfileInfoDTO profileInfoDTO=profileService.toProfileInfoDTO(profile);
        List<ReportEntity> reportList = reportRepository.getReportEntitiesByProfileId(id);
        List<ResponseReportInfoDTO> list= new ArrayList<>();
        for (ReportEntity report : reportList) {
            list.add(toResponseInfo(report,profileInfoDTO));
        }
        return list;

    }

    private ReportEntity get(Integer id) {
        return reportRepository.findReportById(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });

    }
    private ResponseReportDTO toResponse(ReportEntity report) {
        return new ResponseReportDTO(
                report.getId(),
                report.getContent(),
                report.getEntityId(),
                report.getProfileId(),
                report.getVideoType(),
                report.getCreatedDate()
        );
    }
    private ResponseReportInfoDTO toResponseInfo(ReportEntity report, ProfileInfoDTO profileInfoDTO){
        return new ResponseReportInfoDTO(
                report.getId(),
                report.getContent(),
                report.getEntityId(),
                profileInfoDTO,
                report.getVideoType(),
                report.getCreatedDate()
        );

    }
}
