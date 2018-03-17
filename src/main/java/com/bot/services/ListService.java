package com.bot.services;

import com.bot.commons.Messages;
import com.bot.commons.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ListService {

    private static final Logger log = LogManager.getLogger(ListService.class);
    private static final String FILE_NAME = "list.txt";
    private static ListService listService = null;
    private Messages msg = Messages.getInstance();

    public static ListService getInstance() {
        if (listService == null) {
            listService = new ListService();
        }
        return listService;
    }

    public String processListRequest(String request) {
        String response = StringUtils.EMPTY;
        String option = request.split(" ")[0].toUpperCase();
        String stringList = Utils.removeFirstWord(request, " ");
        List<String> list = Arrays
                .stream(stringList.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        switch (option) {
            case "ADD":
                addToList(list);
                break;
            case "QUIT":
                response = removeFromList(list);
                break;
            case "SHOW":
                response = showList();
                break;
            case "DEL":
                if (deleteList()) {
                    response = msg.listDeleted();
                } else {
                    response = msg.listNotExist();
                }
                break;
            default:
                response = msg.listOptionNotValid(option);
        }
        return response;
    }

    private String showList() {
        String response;
        if (fileExists()) {
            List<String> list = getListFromFile();
            if (!list.isEmpty()){
                response = formatList(list);
            } else {
                response = msg.listNotExist();
            }
        } else {
            response = msg.listNotExist();
        }
        return response;
    }

    private List<String> getListFromFile() {
        File listFile = new File(FILE_NAME);
        List<String> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(listFile));
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            log.error(String.format("List file not found (%s)", FILE_NAME), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return list;

    }

    private String formatList(List<String> list) {
        StringBuilder formattedList = new StringBuilder();
        formattedList.append("");
        for (String item : list) {
            formattedList.append("- ");
            formattedList.append(item.trim());
            formattedList.append("\n");
        }
        return formattedList.toString();
    }

    private void addToList(List<String> list) {
        List<String> currentList = new ArrayList<>();
        if (fileExists()) {
            currentList = getListFromFile();
            deleteList();
        }
        PrintWriter pw;
        File listFile = new File(FILE_NAME);
        currentList.addAll(list);
        try {
            pw = new PrintWriter(new FileOutputStream(listFile));
            for (String item : currentList)
                pw.println(item);
            pw.close();
        } catch (FileNotFoundException e) {
            log.error(String.format("List file not found (%s)", FILE_NAME), e);
        }
    }

    private String removeFromList(List<String> itemsToRemove) {
        if (fileExists()) {
            List<String> currentList = getListFromFile();
            List<String> updatedList = new ArrayList<>();
            deleteList();
            log.info(itemsToRemove);
            for (String item : currentList) {
                if (!itemsToRemove.contains(item.trim())) {
                    log.info(item);
                    updatedList.add(item);
                }
            }
            addToList(updatedList);
            return formatList(updatedList);
        } else {
            return msg.listNotExist();
        }
    }

    private boolean deleteList() {
        File listFile = new File(FILE_NAME);
        try {
            return Files.deleteIfExists(listFile.toPath());
        } catch (IOException e) {
            return false;
        }
    }

    private boolean fileExists() {
        return Files.exists(Paths.get(FILE_NAME));
    }
}
