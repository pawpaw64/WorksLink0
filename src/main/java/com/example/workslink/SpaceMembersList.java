package com.example.workslink;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpaceMembersList {
    private String  spaceNames;
    private List<String> membersList;

    public SpaceMembersList(String spaceNames) {
        this.spaceNames = spaceNames;
        String[] spaceNamesArray = spaceNames.split(",");
        this.membersList = new ArrayList<>(Arrays.asList(spaceNamesArray));
    }

    public List<String> getMembersList() {
        for(String x:membersList){
            System.out.println(x);
        }
        return membersList;
    }



}


