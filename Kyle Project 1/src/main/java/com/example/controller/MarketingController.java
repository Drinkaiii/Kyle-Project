package com.example.controller;

import com.example.dto.marketing.CampaignDto;
import com.example.service.marketing.MarketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/marketing")
@RequiredArgsConstructor
public class MarketingController {

    private final MarketingService marketingService;

    //review all hot products for front-end
    @GetMapping("/hots")
    public Object showAllHots(){
        return marketingService.showAllHots();
    }

    //review all campaigns for front-end
    @GetMapping("/campaigns")
    public Object showAllCampaigns(){
        return marketingService.showAllCampaigns();
    }

    @PostMapping("/setCampaign")
    public Object setCampaign(CampaignDto campaignDto){
        return marketingService.setCampaign(campaignDto);
    }

    @PostMapping("/deleteCampaign")
    public Object deleteCampaign(){
        return "delete campaign.html";
    }

}
