package com.asdf.funny.images.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Attachments
{
    protected Long id;
    private String mongodbFileId;//
    private Long busiId;//

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long categoryId;//
    private String categoryName;//
    private String categoryCode;//
    private String busiAliasName;//
    private String busiAlias;//
    private String fileName;//
    private String databaseName;//
    private String collection;//
    private Long fileSize;//
    private String fileType;//
    private Long creatorId;//
    private String creatorName;//
    private Integer year;//
    private String areaCode;//
    private String shardKey;//
    private String saveType;//
    private String litimgUrl;//
    private String url;//
    private String description;//
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date createTime;//
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date updateTime;//
    private String deleteFlag;//
    public void setMongodbFileId(String mongodbFileId)
    {
        this.mongodbFileId=mongodbFileId;
    }
    public String getMongodbFileId()
    {
        return this.mongodbFileId;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName=categoryName;
    }
    public String getCategoryName()
    {
        return this.categoryName;
    }
    public void setCategoryCode(String categoryCode)
    {
        this.categoryCode=categoryCode;
    }
    public String getCategoryCode()
    {
        return this.categoryCode;
    }
    public void setBusiAliasName(String busiAliasName)
    {
        this.busiAliasName=busiAliasName;
    }
    public String getBusiAliasName()
    {
        return this.busiAliasName;
    }
    public void setBusiAlias(String busiAlias)
    {
        this.busiAlias=busiAlias;
    }
    public String getBusiAlias()
    {
        return this.busiAlias;
    }
    public void setFileName(String fileName)
    {
        this.fileName=fileName;
    }
    public String getFileName()
    {
        return this.fileName;
    }
    public void setDatabaseName(String databaseName)
    {
        this.databaseName=databaseName;
    }
    public String getDatabaseName()
    {
        return this.databaseName;
    }
    public void setCollection(String collection)
    {
        this.collection=collection;
    }
    public String getCollection()
    {
        return this.collection;
    }

    public void setFileType(String fileType)
    {
        this.fileType=fileType;
    }
    public String getFileType()
    {
        return this.fileType;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName=creatorName;
    }
    public String getCreatorName()
    {
        return this.creatorName;
    }
    public void setYear(Integer year)
    {
        this.year=year;
    }
    public Integer getYear()
    {
        return this.year;
    }
    public void setAreaCode(String areaCode)
    {
        this.areaCode=areaCode;
    }
    public String getAreaCode()
    {
        return this.areaCode;
    }
    public void setShardKey(String shardKey)
    {
        this.shardKey=shardKey;
    }
    public String getShardKey()
    {
        return this.shardKey;
    }
    public void setSaveType(String saveType)
    {
        this.saveType=saveType;
    }
    public String getSaveType()
    {
        return this.saveType;
    }
    public void setLitimgUrl(String litimgUrl)
    {
        this.litimgUrl=litimgUrl;
    }
    public String getLitimgUrl()
    {
        return this.litimgUrl;
    }
    public void setUrl(String url)
    {
        this.url=url;
    }
    public String getUrl()
    {
        return this.url;
    }
    public void setDescription(String description)
    {
        this.description=description;
    }
    public String getDescription()
    {
        return this.description;
    }
    public void setCreateTime(java.util.Date createTime)
    {
        this.createTime=createTime;
    }
    public java.util.Date getCreateTime()
    {
        return this.createTime;
    }
    public void setUpdateTime(java.util.Date updateTime)
    {
        this.updateTime=updateTime;
    }
    public java.util.Date getUpdateTime()
    {
        return this.updateTime;
    }
    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag=deleteFlag;
    }
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public Long getBusiId() {
        return busiId;
    }

    public void setBusiId(Long busiId) {
        this.busiId = busiId;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    //TODO
}
