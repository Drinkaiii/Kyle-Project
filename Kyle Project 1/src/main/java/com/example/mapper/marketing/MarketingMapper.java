package com.example.mapper.marketing;

import com.example.dto.marketing.CampaignDto;
import com.example.dto.marketing.HotDto;

import java.util.List;

public interface MarketingMapper {

    CampaignDto setCampaign(CampaignDto campaignDto);

    List<CampaignDto> getAllCampaigns();

    HotDto setHot(HotDto hotDto);

    List<HotDto> getAllHots();


}
