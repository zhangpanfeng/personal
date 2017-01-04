package com.darren.personal.service;

import java.util.List;

public interface BaseService {

    /**
     * insert the data to database, if exist, jump it
     * 
     * @param contentList
     */
    public void insertFromFile(List<String> contentList);

    /**
     * insert the data to database, if exist, update it
     * 
     * @param contentList
     */
    public void updateFromFile(List<String> contentList);
}
