package com.example.mapper.marketing;

import com.example.dto.marketing.CampaignDto;
import com.example.dto.marketing.HotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketingMapperImp implements MarketingMapper {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public CampaignDto setCampaign(CampaignDto campaignDto) {
        String sql = "INSERT INTO campaign (product_id,picture,story) VALUE (:product_id,:picture,:story);";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameters.addValue("product_id", campaignDto.getProductId());
        parameters.addValue("picture", campaignDto.getPictureUrl());
        parameters.addValue("story", campaignDto.getStory());
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        campaignDto.setId(keyHolder.getKey().intValue());
        return campaignDto;
    }

    @Override
    public List<CampaignDto> getAllCampaigns() {
        String sql = "SELECT * FROM campaign;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<CampaignDto> campaignDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<CampaignDto>) (rs, rowNum) -> {
            CampaignDto campaignDto = new CampaignDto();
            campaignDto.setId(rs.getInt("id"));
            campaignDto.setProductId(rs.getInt("product_id"));
            campaignDto.setPictureUrl(rs.getString("picture"));
            campaignDto.setStory(rs.getString("story"));
            return campaignDto;
        });
        if (campaignDtos.size() > 0)
            return campaignDtos;
        else
            return null;

    }

    @Override
    public HotDto setHot(HotDto hotDto) {
        return null;
    }

    @Override
    public List<HotDto> getAllHots() {
        String sql = "SELECT * FROM hot;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<HotDto> hotDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<HotDto>) (rs, rowNum) -> {
            HotDto hotDto = new HotDto();
            hotDto.setId(rs.getInt("id"));
            hotDto.setTitle(rs.getString("title"));
            hotDto.setProductId(rs.getInt("product_id"));
            return hotDto;
        });
        if (hotDtos.size() > 0)
            return hotDtos;
        else
            return null;
    }
}
