package com.thinkbiganalytics.feedmgr.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkbiganalytics.feedmgr.rest.model.FeedCategory;
import com.thinkbiganalytics.feedmgr.rest.model.FeedMetadata;
import com.thinkbiganalytics.feedmgr.rest.model.RegisteredTemplate;

/**
 * Created by sr186054 on 2/23/16.
 */
public class FileObjectPersistence {

    private static String filePath = "/tmp";
    private static String FEED_METADATA_FILENAME = "feed-metadata.json";
    private static String FEED_CATEGORIES_FILENAME = "feed-categories.json";
    private static String TEMPLATE_METADATA_FILENAME = "registered-templates.json";

    private static class LazyHolder {
        static final FileObjectPersistence INSTANCE = new FileObjectPersistence();
    }

    public static FileObjectPersistence getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void writeCategoriesToFile(Collection<FeedCategory> categories){

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+FEED_CATEGORIES_FILENAME);
        try {
            mapper.writeValue(file,categories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFeedsToFile(Collection<FeedMetadata> feeds){

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+FEED_METADATA_FILENAME);
        try {
            mapper.writeValue(file,feeds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTemplatesToFile(Collection<RegisteredTemplate> templates){

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+TEMPLATE_METADATA_FILENAME);
        try {
            mapper.writeValue(file,templates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<FeedCategory> getCategoriesFromFile(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+FEED_CATEGORIES_FILENAME);
        Collection<FeedCategory> categories = null;
        if(file.exists()) {
            try {
                categories = mapper.readValue(file,new TypeReference<List<FeedCategory>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public Collection<FeedMetadata> getFeedsFromFile(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+FEED_METADATA_FILENAME);
        Collection<FeedMetadata> feeds = null;
        if(file.exists()) {
            try {
                feeds = mapper.readValue(file,  new TypeReference<List<FeedMetadata>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return feeds;
    }

    public Collection<RegisteredTemplate> getTemplatesFromFile(){
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath+"/"+TEMPLATE_METADATA_FILENAME);
        Collection<RegisteredTemplate> templates = null;
        if(file.exists()) {
            try {
                templates = mapper.readValue(file, new TypeReference<List<RegisteredTemplate>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return templates;
    }
}