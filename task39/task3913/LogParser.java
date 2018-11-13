package com.javarush.task.task39.task3913;


import java.io.*;
import java.nio.file.*;

import com.javarush.task.task39.task3913.query.DateQuery;
import com.javarush.task.task39.task3913.query.EventQuery;
import com.javarush.task.task39.task3913.query.IPQuery;
import com.javarush.task.task39.task3913.query.UserQuery;


import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery {
    private Path logDir;
    private List<String> allLogFileAsString = new ArrayList<>();

    public LogParser(Path logDir) throws IOException {
        this.logDir = logDir;
        extractFile(logDir);
    }

    public void extractFile(Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (Files.isRegularFile(file) && file.toString().endsWith("log")) {
                    File f = file.toFile();
                    FileReader reader = new FileReader(f);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        allLogFileAsString.add(line);
                        line = bufferedReader.readLine();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }


    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {

        Set<String> allIP = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before))
                    allIP.add(currentIP);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return allIP.size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> allIP = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before))
                    allIP.add(currentIP);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return allIP;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        Set<String> allIP = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");

                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }

            try {
                if (filterBetweenDate(dataAndTime, after, before) && user.equals(userInLine))
                    allIP.add(currentIP);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return allIP;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        Set<String> allIP = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
            }

            try {
                if (filterBetweenDate(dataAndTime, after, before) && event.toString().equalsIgnoreCase(eventFromLine))
                    allIP.add(currentIP);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return allIP;

    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        Set<String> allIP = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String statusFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                statusFromLine = lineAsMassiv[lineAsMassiv.length - 1];

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                statusFromLine = lineAsMassiv[lineAsMassiv.length - 1];
            }

            try {
                if (filterBetweenDate(dataAndTime, after, before) && status.toString().equalsIgnoreCase(statusFromLine))
                    allIP.add(currentIP);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return allIP;
    }


    public boolean filterBetweenDate(String data, Date after, Date before) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date parsedData = simpleDateFormat.parse(data);
        if (after == null)
            after = new Date(0L);
        if (before == null)
            before = new Date(Long.MAX_VALUE);
        if (parsedData.compareTo(after) >= 0 && parsedData.compareTo(before) <= 0)
            return true;
        else return false;
    }

    public Date convertStringToDate(String data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date parsedData = simpleDateFormat.parse(data);
        return parsedData;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> users = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();

            } else {
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");

                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            users.add(userInLine);
        }

        return users;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before))
                    users.add(userInLine);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return users.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> set = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && user.equals(userInLine))
                    set.add(eventFromLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return set.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && ip.equals(currentIP))
                    users.add(userInLine);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return users;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> loggedUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.LOGIN.toString().equals(eventFromLine))
                    loggedUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return loggedUsers;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> downloadedPluginUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.DOWNLOAD_PLUGIN.toString().equals(eventFromLine))
                    downloadedPluginUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return downloadedPluginUsers;
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set<String> wroteMessageUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.WRITE_MESSAGE.toString().equals(eventFromLine))
                    wroteMessageUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return wroteMessageUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.SOLVE_TASK.toString().equals(eventFromLine))
                    solvedTaskUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return solvedTaskUsers;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> solvedTaskUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;

            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            String taskFromLine = null;
            if (Event.SOLVE_TASK.toString().equals(eventFromLine)) {
                taskFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.SOLVE_TASK.toString().equals(eventFromLine) && task == Integer.parseInt(taskFromLine))
                    solvedTaskUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return solvedTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> doneTaskUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.DONE_TASK.toString().equals(eventFromLine))
                    doneTaskUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return doneTaskUsers;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> doneTaskUsers = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            String status = null;

            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                status = lineAsMassiv[lineAsMassiv.length - 1];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                status = lineAsMassiv[lineAsMassiv.length - 1];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            String taskFromLine = null;
            if (Event.DONE_TASK.toString().equals(eventFromLine)) {
                taskFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.DONE_TASK.toString().equals(eventFromLine) && task == Integer.parseInt(taskFromLine))
                    doneTaskUsers.add(userInLine);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return doneTaskUsers;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && event.toString().equals(eventFromLine) && user.equals(userInLine))
                    dates.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String status = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                status = lineAsMassiv[lineAsMassiv.length - 1];

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                status = lineAsMassiv[lineAsMassiv.length - 1];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Status.FAILED.name().equals(status))
                    dates.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String status = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                status = lineAsMassiv[lineAsMassiv.length - 1];

            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                status = lineAsMassiv[lineAsMassiv.length - 1];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Status.ERROR.name().equals(status))
                    dates.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {

        List<Date> list = new ArrayList<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.LOGIN.toString().equals(eventFromLine) && user.equals(userInLine))
                    list.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Collections.sort(list);
        }
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {

        List<Date> list = new ArrayList<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.SOLVE_TASK.toString().equals(eventFromLine) && user.equals(userInLine) &&
                        task == Integer.parseInt(taskFromLine))
                    list.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Collections.sort(list);
        }
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        List<Date> list = new ArrayList<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.DONE_TASK.toString().equals(eventFromLine) && user.equals(userInLine) &&
                        task == Integer.parseInt(taskFromLine))
                    list.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Collections.sort(list);
        }
        if (list.isEmpty())
            return null;
        else
            return list.get(0);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.WRITE_MESSAGE.toString().equals(eventFromLine) && user.equals(userInLine))
                    dates.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before) && Event.DOWNLOAD_PLUGIN.toString().equals(eventFromLine) && user.equals(userInLine))
                    dates.add(convertStringToDate(dataAndTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after,before).size();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before))
                   events.add(Event.valueOf(eventFromLine));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String currentIP = lineAsMassiv[0];
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&ip.equals(currentIP))
                    events.add(Event.valueOf(eventFromLine));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String userInLine = null;
            StringBuffer stringBuffer = new StringBuffer();
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                for (int i = 1; i < lineAsMassiv.length - 4; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                for (int i = 1; i < lineAsMassiv.length - 5; i++) {
                    String parthOfUser = lineAsMassiv[i];
                    stringBuffer.append(parthOfUser + " ");
                }
                userInLine = stringBuffer.toString();
                userInLine = userInLine.trim();
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&user.equals(userInLine))
                    events.add(Event.valueOf(eventFromLine));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String status = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                status=lineAsMassiv[lineAsMassiv.length - 1];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                status=lineAsMassiv[lineAsMassiv.length - 1];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&Status.FAILED.name().equals(status))
                    events.add(Event.valueOf(eventFromLine));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String status = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
                status=lineAsMassiv[lineAsMassiv.length - 1];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                status=lineAsMassiv[lineAsMassiv.length - 1];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&Status.ERROR.name().equals(status))
                    events.add(Event.valueOf(eventFromLine));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int count=0;
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;
            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine=lineAsMassiv[lineAsMassiv.length - 2];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&Event.SOLVE_TASK.name().equals(eventFromLine)&&task==Integer.parseInt(taskFromLine))
                    count++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        int count=0;
        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;

            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine=lineAsMassiv[lineAsMassiv.length - 2];
            }
            try {
                if (filterBetweenDate(dataAndTime, after, before)&&Event.DONE_TASK.name().equals(eventFromLine)&&task==Integer.parseInt(taskFromLine))
                    count++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        Map<Integer,Integer> map=new HashMap<>();

        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;

            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine=lineAsMassiv[lineAsMassiv.length - 2];
            }
            if(taskFromLine!=null) {
                Integer task = Integer.parseInt(taskFromLine);
                try {
                    if (filterBetweenDate(dataAndTime, after, before) && Event.SOLVE_TASK.name().equals(eventFromLine)) {
                        if (!map.containsKey(task)) {
                            map.put(task, 1);
                        } else {
                            Integer oldValue = map.get(task);
                            map.remove(task);
                            Integer newValue = oldValue + 1;
                            map.put(task, newValue);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        Map<Integer,Integer> map=new HashMap<>();

        for (String s : allLogFileAsString) {
            String[] lineAsMassiv = s.split("\\t?\\s");
            String unknow = lineAsMassiv[lineAsMassiv.length - 3];
            String dataAndTime = null;
            String eventFromLine = null;
            String taskFromLine = null;

            if (unknow.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 4] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 2];
            } else {
                unknow = lineAsMassiv[lineAsMassiv.length - 4];
                dataAndTime = lineAsMassiv[lineAsMassiv.length - 5] + " " + unknow;
                eventFromLine = lineAsMassiv[lineAsMassiv.length - 3];
                taskFromLine=lineAsMassiv[lineAsMassiv.length - 2];
            }
            if(taskFromLine!=null) {
                Integer task = Integer.parseInt(taskFromLine);
                try {
                    if (filterBetweenDate(dataAndTime, after, before) && Event.DONE_TASK.name().equals(eventFromLine)) {
                        if (!map.containsKey(task)) {
                            map.put(task, 1);
                        } else {
                            Integer oldValue = map.get(task);
                            map.remove(task);
                            Integer newValue = oldValue + 1;
                            map.put(task, newValue);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}