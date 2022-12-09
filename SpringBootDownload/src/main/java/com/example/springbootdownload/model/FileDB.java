package com.example.springbootdownload.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name= "fileDB")
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;

    private String name;


    private String type;

    @Lob
    /**
     @Lob : LOB là kiểu dữ liệu để lưu trữ dữ liệu đối tượng lớn
            Có 2 loại LOB : BLOB và CLOB
     */

    private byte[] data;

    public FileDB() {
    }

    public FileDB( String name,String type, byte[] data) {
        this.name = name;
        this.data = data;
        this.type = type;
    }

}

