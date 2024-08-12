package com.example.service.marketing;

import com.example.dto.marketing.CampaignDto;

public interface MarketingService {

    Object setCampaign(CampaignDto campaignDto);

    Object showAllCampaigns();

    Object showAllHots();

}
