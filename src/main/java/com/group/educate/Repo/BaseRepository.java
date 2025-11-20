package com.group.educate.Repo;

import java.io.*;
import java.util.*;

public class BaseRepository extends FileManager {

    public static <T> T findbyObject(String fileName, T object) throws Exception {
        for (Object t : FileManager.read(object.getClass(), fileName)) {
            if (t.equals(object)) {
                return (T) t;
            }
        }
        return null;
    }


}
