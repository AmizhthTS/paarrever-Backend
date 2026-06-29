package com.paarr.service.implementation;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.paarr.dto.FeedbackDTO;
import com.paarr.dto.FeedbackPageDTO;
import com.paarr.dto.ResponseDTO;
import com.paarr.entity.FeedbackModel;
import com.paarr.exception.EcosystemException;
import com.paarr.exception.ErrorEnum;
import com.paarr.repository.FeedbackRepository;
import com.paarr.service.FeedbackService;
import com.paarr.util.AWSUtil;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AWSUtil awsUtil;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ResponseDTO save(FeedbackDTO feedbackDTO) {

        FeedbackModel feedbackModel = null;
        String image = null;

        if (feedbackDTO.getImage() != null && feedbackDTO.getImage().length > 0) {

            if (!Arrays.equals(feedbackDTO.getImage(), "something".getBytes())) {

                ByteArrayInputStream targetStream =
                        new ByteArrayInputStream(feedbackDTO.getImage());

                try {
                    if (!awsUtil.checkFileSize(targetStream))
                        throw new EcosystemException(ErrorEnum.INVALID_FILE_SIZE);
                } catch (Exception e) {
                    throw new EcosystemException(ErrorEnum.INVALID_FILE_SIZE);
                }

                if (feedbackDTO.getImageName().isEmpty()
                        || !feedbackDTO.getImageName().contains("."))
                    throw new EcosystemException(ErrorEnum.INVALID_FILE_NAME);

                String fullFileName = feedbackDTO.getImageName();
                String[] fileArray = fullFileName.split("[.]");

                String fileName = fileArray[0];
                String fileFormat = fileArray[1];

                try {
                    image = awsUtil.saveFile(
                            targetStream,
                            fileName,
                            fileFormat,
                            "profile");
                } catch (Exception e) {
                    throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
                }

                if (image.isEmpty()) {
                    throw new EcosystemException(ErrorEnum.FILE_UPLOAD_FAILED);
                } else {

                    if (feedbackDTO.getId() != null) {

                        FeedbackModel existing =
                                feedbackRepository.findById(
                                        feedbackDTO.getId()).orElse(null);

                        if (existing != null
                                && existing.getImage() != null
                                && !existing.getImage().isEmpty()) {

                            try {
                                awsUtil.copyAndTrashBucketObject(
                                        existing.getImage(),
                                        existing.getImage(),
                                        "profile");

                            } catch (Exception e) {
                                logger.warn("File Not Found to remove from AWS :: "
                                        + existing.getImage());
                            }
                        }
                    }
                }

            } else {

                if (feedbackDTO.getId() != null) {

                    FeedbackModel existing =
                            feedbackRepository.findById(
                                    feedbackDTO.getId()).orElse(null);

                    if (existing != null) {
                        image = existing.getImage();
                    }
                }
            }
        }

        if (feedbackDTO.getId() != null && feedbackDTO.getId() > 0) {
            feedbackModel =
                    feedbackRepository.findById(
                            feedbackDTO.getId())
                            .orElse(new FeedbackModel());
        } else {
            feedbackModel = new FeedbackModel();
            feedbackModel.setActive(true);
        }

        feedbackModel.setFirstName(feedbackDTO.getFirstName());
        feedbackModel.setLastName(feedbackDTO.getLastName());
        feedbackModel.setPhoneNumber(feedbackDTO.getPhoneNumber());
        feedbackModel.setEmail(feedbackDTO.getEmail());
        feedbackModel.setFeedbacktype(feedbackDTO.getFeedbackType());

        feedbackModel.setBranchName(feedbackDTO.getBranchName());

        feedbackModel.setBoughtItems(feedbackDTO.getBoughtItems());

        feedbackModel.setMenuRating(feedbackDTO.getMenuRating());
        feedbackModel.setFoodRating(feedbackDTO.getFoodRating());
        feedbackModel.setStaffRating(feedbackDTO.getStaffRating());
        feedbackModel.setServiceRating(feedbackDTO.getServiceRating());

        feedbackModel.setFeedbackMessage(feedbackDTO.getFeedbackMessage());

        feedbackModel.setImage(image);

        feedbackModel = feedbackRepository.save(feedbackModel);

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setResponseStatus("Success");

        responseDTO.setResponseMessage(
                feedbackDTO.getId() != null
                        && feedbackDTO.getId() > 0
                                ? "Updated Successfully"
                                : "Saved Successfully");

        responseDTO.setResponse(feedbackModel);

        return responseDTO;
    }

    @Override
    public FeedbackDTO get(Long id) {

        FeedbackModel model =
                feedbackRepository.findByIdAndActive(id, true);

        FeedbackDTO dto = new FeedbackDTO();

        dto.setId(model.getId());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setPhoneNumber(model.getPhoneNumber());
        dto.setEmail(model.getEmail());
        dto.setFeedbackType(model.getFeedbacktype());
        dto.setBranchName(model.getBranchName());
        dto.setBoughtItems(model.getBoughtItems());

        dto.setMenuRating(model.getMenuRating());
        dto.setFoodRating(model.getFoodRating());
        dto.setStaffRating(model.getStaffRating());
        dto.setServiceRating(model.getServiceRating());

        dto.setFeedbackMessage(model.getFeedbackMessage());

        dto.setImageName(
            awsUtil.getpreSignedFile(5, model.getImage(), "profile")
        );

        return dto;
    }

    @Override
    public ResponseDTO delete(Long id) {

        FeedbackModel model =
                feedbackRepository.findByIdAndActive(id, true);

        model.setActive(false);

        feedbackRepository.save(model);

        ResponseDTO response = new ResponseDTO();
        response.setResponseStatus("Success");
        response.setResponseMessage("Deleted Successfully");

        return response;
    }

    @Override
public FeedbackPageDTO list(FeedbackPageDTO feedbackPageDTO) {

    Pageable paging = PageRequest.of(
            Math.max(feedbackPageDTO.getPageNumber() - 1, 0),
            feedbackPageDTO.getListSize() > 0 ? feedbackPageDTO.getListSize() : 25,
            Sort.by("id").descending()
    );

    Page<FeedbackModel> page;

    String search = feedbackPageDTO.getSearchString();
    boolean hasSearch = (search != null && !search.trim().isEmpty());

    String branchName = feedbackPageDTO.getBranchName();
    boolean hasBranch =
            (branchName != null && !branchName.trim().isEmpty());

    long feedbackId = feedbackPageDTO.getFeedbackId();

    if (feedbackId > 0 && hasBranch) {

        page = feedbackRepository
                .findByIdAndBranchNameContainingIgnoreCaseAndActive(
                        feedbackId,
                        branchName,
                        true,
                        paging);

    }
   
    else if (feedbackId > 0) {

        page = feedbackRepository
                .findByIdAndActive(
                        feedbackId,
                        true,
                        paging);

    }
    else if (hasBranch) {

        page = feedbackRepository
                .findByBranchNameContainingIgnoreCaseAndActive(
                        branchName,
                        true,
                        paging);

    }
   
    else {

        page = feedbackRepository
                .findByActive(
                        true,
                        paging);
    }

    List<FeedbackDTO> feedbackDTOList = page.stream()
            .map(this::constructResponse)
            .collect(Collectors.toList());

    feedbackPageDTO.setFeedbacks(feedbackDTOList);
    feedbackPageDTO.setCount(page.getTotalElements());
    feedbackPageDTO.setTotalPages(page.getTotalPages());

    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setResponseStatus("Success");
    responseDTO.setResponseMessage("List Fetched");

    feedbackPageDTO.setResponse(responseDTO);

    return feedbackPageDTO;
}
    private FeedbackDTO constructResponse(FeedbackModel feedbackModel) {

        FeedbackDTO feedbackDTO = new FeedbackDTO();

        feedbackDTO.setId(feedbackModel.getId());

        feedbackDTO.setFirstName(feedbackModel.getFirstName());
        feedbackDTO.setLastName(feedbackModel.getLastName());
        feedbackDTO.setPhoneNumber(feedbackModel.getPhoneNumber());
        feedbackDTO.setEmail(feedbackModel.getEmail());
       feedbackDTO.setFeedbackType(feedbackModel.getFeedbacktype());
        feedbackDTO.setBranchName(feedbackModel.getBranchName());

        // LIST OF ITEMS BOUGHT
        feedbackDTO.setBoughtItems(feedbackModel.getBoughtItems());

        feedbackDTO.setMenuRating(feedbackModel.getMenuRating());
        feedbackDTO.setFoodRating(feedbackModel.getFoodRating());
        feedbackDTO.setStaffRating(feedbackModel.getStaffRating());
        feedbackDTO.setServiceRating(feedbackModel.getServiceRating());

        feedbackDTO.setFeedbackMessage(feedbackModel.getFeedbackMessage());

        if (feedbackModel.getImage() != null
                && !feedbackModel.getImage().isEmpty()) {

            String fileName = feedbackModel.getImage();

            String preSignedFileUrl =
                    awsUtil.getpreSignedFile(
                            5,
                            fileName,
                            "profile");

            if (!preSignedFileUrl.isEmpty()) {
                feedbackDTO.setImageName(preSignedFileUrl);
            } else {
                logger.warn("Get Image File URL Failed " + fileName);
            }
        }

        return feedbackDTO;
    }
}