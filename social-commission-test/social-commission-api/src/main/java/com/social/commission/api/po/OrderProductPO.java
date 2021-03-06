package com.social.commission.api.po;

import java.math.BigDecimal;

public class OrderProductPO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.order_product_id
     *
     * @mbg.generated
     */
    private Long orderProductId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.son_order_id
     *
     * @mbg.generated
     */
    private String sonOrderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.total_price
     *
     * @mbg.generated
     */
    private BigDecimal totalPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.pic_url
     *
     * @mbg.generated
     */
    private String picUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.sku_id
     *
     * @mbg.generated
     */
    private String skuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_product.product_name
     *
     * @mbg.generated
     */
    private String productName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.order_product_id
     *
     * @return the value of order_product.order_product_id
     *
     * @mbg.generated
     */
    public Long getOrderProductId() {
        return orderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.order_product_id
     *
     * @param orderProductId the value for order_product.order_product_id
     *
     * @mbg.generated
     */
    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.son_order_id
     *
     * @return the value of order_product.son_order_id
     *
     * @mbg.generated
     */
    public String getSonOrderId() {
        return sonOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.son_order_id
     *
     * @param sonOrderId the value for order_product.son_order_id
     *
     * @mbg.generated
     */
    public void setSonOrderId(String sonOrderId) {
        this.sonOrderId = sonOrderId == null ? null : sonOrderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.total_price
     *
     * @return the value of order_product.total_price
     *
     * @mbg.generated
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.total_price
     *
     * @param totalPrice the value for order_product.total_price
     *
     * @mbg.generated
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.pic_url
     *
     * @return the value of order_product.pic_url
     *
     * @mbg.generated
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.pic_url
     *
     * @param picUrl the value for order_product.pic_url
     *
     * @mbg.generated
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.sku_id
     *
     * @return the value of order_product.sku_id
     *
     * @mbg.generated
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.sku_id
     *
     * @param skuId the value for order_product.sku_id
     *
     * @mbg.generated
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_product.product_name
     *
     * @return the value of order_product.product_name
     *
     * @mbg.generated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_product.product_name
     *
     * @param productName the value for order_product.product_name
     *
     * @mbg.generated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }
}