package com.group.InternMap.Repo;

import com.group.InternMap.Model.User.User;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

public class BaseRepository<T> extends FileManager {

    private final String fileName;
    private final Class<T> type;

    public BaseRepository(Class<T> type, String fileName) {
        this.fileName = fileName;
        this.type = type;
    }

    public List<T> findAll() {
        try {
            return read(type, fileName);
        } catch (Exception e) {
            try {
                write(fileName, null);
            } catch (Exception f) {
                System.out.println("Failed to Create file to save data");
            }
        }
        try {
            return read(type, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAll(List<T> items) throws Exception {
        write(fileName, items.toArray((T[]) Array.newInstance(type, 0)));
    }

    public List<T> search(Predicate<T> condition) throws Exception {
        return  findAll().stream()
                .filter(condition)
                .toList();
    }

    public T findOne(List<T> items, Predicate<T> condition) {
        for (T item : items) {
            if (condition.test(item)) {
                return item;   // return first match
            }
        }
        return null;
    }


}
