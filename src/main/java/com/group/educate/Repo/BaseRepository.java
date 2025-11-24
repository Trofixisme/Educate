package com.group.educate.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.*;


public class BaseRepository<T> extends FileManager{

    private final String fileName;
    private final Class<T> type;

    public BaseRepository(Class<T> type, String fileName) {
        this.fileName = fileName;
        this.type = type;
    }

    public List<T> findAll() throws Exception {
        try {
            return read(type, fileName);
        } catch (Exception e) {
            write(fileName, null);
        }
        return read(type, fileName);
    }

    public void saveAll(List<T> items) throws Exception {
        write(fileName, items.toArray((T[]) Array.newInstance(type, 0)));
    }
}
