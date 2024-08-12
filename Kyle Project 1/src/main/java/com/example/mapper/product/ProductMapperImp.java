package com.example.mapper.product;

import com.example.dto.product.ColorDto;
import com.example.dto.product.ProductDto;
import com.example.dto.product.Result;
import com.example.dto.product.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductMapperImp implements ProductMapper {

    @Value("${spring.datasource.url}")
    String urlSQL;
    @Value("${spring.datasource.username}")
    String usernameSQL;
    @Value("${spring.datasource.password}")
    String passwordSQL;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Result setProductTable(ProductDto productDto) {

        String sql = "INSERT INTO product (id, category_id, title, des, price, texture, wash, place_id, note, story, main_image) VALUES (:id, :category_id, :title, :des, :price, :texture, :wash, :place_id, :note, :story, :main_image)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        parameters.addValue("id", productDto.getId());
        parameters.addValue("category_id", productDto.getCategory());
        parameters.addValue("title", productDto.getTitle());
        parameters.addValue("des", productDto.getDescription());
        parameters.addValue("price", productDto.getPrice());
        parameters.addValue("texture", productDto.getTexture());
        parameters.addValue("wash", productDto.getWash());
        parameters.addValue("place_id", productDto.getPlace());
        parameters.addValue("note", productDto.getNote());
        parameters.addValue("story", productDto.getStory());
        parameters.addValue("main_image", productDto.getImageMainUrl());
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        productDto.setId(keyHolder.getKey().intValue());
        return Result.success(productDto);
    }

    @Override
    public Result setColorTable(ProductDto productDto) {
        String sql = "INSERT INTO color (name, code) VALUES (:name, :code)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        List<Variant> variants = productDto.getVariants();
        List<ColorDto> colorDtos = productDto.getColorDtos();
        for (int i = 0; i < variants.size(); i++) {
            parameters.addValue("name", variants.get(i).getColorName());
            parameters.addValue("code", variants.get(i).getColorCode());
            namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
            colorDtos.get(i).setId(keyHolder.getKey().intValue());
        }
        productDto.setColorDtos(colorDtos);
        return Result.success(productDto);
    }

    @Override
    public Result setProductColorTable(ProductDto productDto) {

        String sql = "INSERT INTO product_color (product_id, color_id) VALUES (:product_id, :color_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<ColorDto> colorDtos = productDto.getColorDtos();
        for (ColorDto colorDto : colorDtos) {
            parameters.addValue("product_id", productDto.getId());
            parameters.addValue("color_id", colorDto.getId());
            namedParameterJdbcTemplate.update(sql, parameters);
        }
        return Result.success(productDto);
    }

    @Override
    public Result setProductSizeTable(ProductDto productDto) {
        String sql = "INSERT INTO product_size (product_id, size_id) VALUES (:product_id, :size_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<String> sizes = productDto.getSizes();
        for (int i = 0; i < sizes.size(); i++) {
            parameters.addValue("product_id", productDto.getId());
            parameters.addValue("size_id", sizes.get(i));
            namedParameterJdbcTemplate.update(sql, parameters);
        }
        return Result.success(productDto);
    }

    @Override
    public Result setImageTable(ProductDto productDto) {
        String sql = "INSERT INTO image (product_id, url) VALUES (:product_id, :url)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<String> urls = productDto.getImagesUrl();
        for (String url : urls) {
            parameters.addValue("product_id", productDto.getId());
            parameters.addValue("url", url);
            namedParameterJdbcTemplate.update(sql, parameters);
        }
        return Result.success(productDto);
    }

    @Override
    public Result setVariantTable(ProductDto productDto) {
        String sql = "INSERT INTO variant (product_id, color_code, size, stock) VALUES (:product_id, :color_code, :size, :stock)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<Variant> variants = productDto.getVariants();
        for (int i = 0; i < variants.size(); i++) {
            List<String> sizes = variants.get(i).getSizes();
            List<Integer> stocks = variants.get(i).getStocks();
            for (int j = 0; j < sizes.size(); j++) {
                parameters.addValue("product_id", productDto.getId());
                System.out.println(variants.get(i).getColorCode());
                parameters.addValue("color_code", variants.get(i).getColorCode());
                parameters.addValue("size", sizes.get(j));
                parameters.addValue("stock", stocks.get(j));
                namedParameterJdbcTemplate.update(sql, parameters);
            }
        }
        return Result.success(productDto);
    }

    @Override
    public Result getProductTableCount() {
        String sql = "SELECT COUNT(*) FROM product";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return Result.success(count);
    }

    @Override
    public Result getProductTableCount(int categoryId) {
        String sql = "SELECT COUNT(*) FROM product WHERE category_id = :categoryId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("categoryId", categoryId);
        int count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return Result.success(count);
    }

    @Override
    public Result getProductTableByRow(int startRow, int numberOfRows) {
        String sql = "SELECT * FROM product LIMIT :numberOfRows OFFSET :startRow";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("numberOfRows", numberOfRows);
        parameters.addValue("startRow", startRow);
        List<ProductDto> productDtos = mapDataToProductDtos(sql, parameters);
        return Result.success(productDtos);
    }

    @Override
    public Result getProductTableByRow(int startRow, int numberOfRows, int categoryId) {
        String sql = "SELECT * FROM product WHERE category_id = :categoryId LIMIT :numberOfRows OFFSET :startRow";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("categoryId", categoryId);
        parameters.addValue("numberOfRows", numberOfRows);
        parameters.addValue("startRow", startRow);
        List<ProductDto> productDtos = mapDataToProductDtos(sql, parameters);
        return Result.success(productDtos);
    }

    @Override
    public Result getProductTableBySearchTitle(int startRow, int numberOfRows, String keyword) {
        String sql = "SELECT * FROM product WHERE title LIKE :keyword LIMIT :numberOfRows OFFSET :startRow";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("keyword", "%" + keyword + "%");
        parameters.addValue("numberOfRows", numberOfRows);
        parameters.addValue("startRow", startRow);
        List<ProductDto> productDtos = mapDataToProductDtos(sql, parameters);
        return Result.success(productDtos);
    }

    @Override
    public Result getProductTableById(int id) {
        String sql = "SELECT * FROM product WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        List<ProductDto> productDtos = mapDataToProductDtos(sql, parameters);
        return Result.success(productDtos);
    }

    @Override
    public Result getProductColorTableByProductId(int productId) {
        String sql = "SELECT * FROM product_color WHERE product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("product_id", productId);
        List<Integer> colorIds = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<Integer>) (rs, rowNum) -> {
            return rs.getInt("color_id");
        });
        return Result.success(colorIds);
    }

    @Override
    public Result getColorTableById(int id) {
        String sql = "SELECT * FROM color WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        ColorDto colorDto = namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<ColorDto>) (rs, rowNum) -> {
            ColorDto cd = new ColorDto();
            cd.setName(rs.getString("name"));
            cd.setCode(rs.getString("code"));
            return cd;
        });
        return Result.success(colorDto);
    }

    @Override
    public Result getProductSizeTableByProductId(int productId) {
        String sql = "SELECT * FROM product_size WHERE product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("product_id", productId);
        List<Integer> sizeIds = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<Integer>) (rs, rowNum) -> {
            return rs.getInt("size_id");
        });
        return Result.success(sizeIds);
    }

    @Override
    public Result getSizeTableById(int id) {
        String sql = "SELECT * FROM size WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        String size = namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<String>) (rs, rowNum) -> {
            return rs.getString("symbol");
        });
        return Result.success(size);
    }

    @Override
    public Result getCategoryTableById(int id) {
        String sql = "SELECT * FROM category WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        String category = namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<String>) (rs, rowNum) -> {
            return rs.getString("type");
        });
        return Result.success(category);
    }

    @Override
    public Result getCategoryTableByType(String type) {
        String sql = "SELECT * FROM category WHERE type = :type";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("type", type);
        String id = namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<String>) (rs, rowNum) -> {
            return rs.getString("id");
        });
        return Result.success(id);
    }

    @Override
    public Result getPlaceTableById(int id) {
        String sql = "SELECT * FROM place WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        String place = namedParameterJdbcTemplate.queryForObject(sql, parameters, (RowMapper<String>) (rs, rowNum) -> {
            return rs.getString("country");
        });
        return Result.success(place);
    }

    @Override
    public Result getImageTableByProductId(int productId) {
        String sql = "SELECT * FROM image WHERE product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("product_id", productId);
        List<String> images = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<String>) (rs, rowNum) -> {
            return rs.getString("url");
        });
        return Result.success(images);
    }

    @Override
    public Result getVariantTableByProductId(int productId) {
        String sql = "SELECT * FROM variant WHERE product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("product_id", productId);
        List<Variant> variants = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<Variant>) (rs, rowNum) -> {
            Variant v = new Variant();
            v.setColorCode(rs.getString("color_code"));
            v.setSize(rs.getString("size"));
            v.setStock(rs.getInt("stock"));
            return v;
        });
        return Result.success(variants);
    }

    private List<ProductDto> mapDataToProductDtos(String sql, MapSqlParameterSource parameters) {
        List<ProductDto> productDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<ProductDto>) (rs, rowNum) -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(rs.getInt("id"));
            productDto.setCategory(rs.getString("category_id"));
            productDto.setTitle(rs.getString("title"));
            productDto.setDescription(rs.getString("des"));
            productDto.setPrice(rs.getInt("price"));
            productDto.setTexture(rs.getString("texture"));
            productDto.setWash(rs.getString("wash"));
            productDto.setPlace(rs.getString("place_id"));
            productDto.setNote(rs.getString("note"));
            productDto.setStory(rs.getString("story"));
            productDto.setImageMainUrl(rs.getString("main_image"));
            return productDto;
        });
        return productDtos;
    }

}
