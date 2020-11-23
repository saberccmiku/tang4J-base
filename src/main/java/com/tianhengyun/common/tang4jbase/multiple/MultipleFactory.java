package com.tianhengyun.common.tang4jbase.multiple;

import org.apache.ibatis.jdbc.SQL;

import java.util.Arrays;
import java.util.List;

public class MultipleFactory {

    public static String makeSelect(Multiple... multiples) {
        List<Multiple> tempMultiples = Arrays.asList(multiples);
        //create column segment
        StringBuilder columnSb = new StringBuilder();
        //create left join segment
        StringBuilder leftJoinSb = new StringBuilder();
        String[] leftJoins = new String[multiples.length];
        for (int m = 0; m < multiples.length; m++) {
            String servantNickName = multiples[m].getServantNickName();
            for (Multiple tempMultiple : tempMultiples) {
                if (tempMultiple.getServantNickName().equalsIgnoreCase(servantNickName)) {
                    servantNickName += m;
                }
            }
            List<String> masterTableFields = multiples[m].getMasterTableFields();
            List<String> masterEntityFields = multiples[m].getMasterEntityFields();
            for (int i = 0; i < masterTableFields.size(); i++) {
                columnSb.append(multiples[m].getMasterNickName()).append(".")
                        .append(masterTableFields.get(i)).append(" as ")
                        .append(masterEntityFields.get(i)).append(",");
            }
            List<String> servantTableFields = multiples[m].getServantTableFields();
            List<String> servantEntityFields = multiples[m].getServantEntityFields();
            for (int i = 0; i < servantTableFields.size(); i++) {
                columnSb.append(servantNickName).append(".")
                        .append(servantTableFields.get(i)).append(" as ")
                        .append(servantEntityFields.get(i)).append(",");
            }
            leftJoinSb.delete(0, leftJoinSb.toString().length());
            leftJoinSb
                    .append(multiples[m].getServantTableName())
                    .append(" as ")
                    .append(servantNickName)
                    .append(" on ")
                    .append(servantNickName)
                    .append(".")
                    .append(multiples[m].getServantColumn())
                    .append(" = ")
                    .append(multiples[m].getMasterNickName())
                    .append(".")
                    .append(multiples[m].getMasterColumn());
            leftJoins[m] = leftJoinSb.toString();
        }

        if (columnSb.toString().endsWith(",")) {
            columnSb.deleteCharAt(columnSb.toString().lastIndexOf(","));
        }

        return new SQL() {
            {
                SELECT(columnSb.toString())
                        .FROM(multiples[0].getMasterTableName() + " as " + multiples[0].getMasterNickName())
                        .LEFT_OUTER_JOIN(leftJoins);
            }
        }.toString();
    }

    public static Multiple joinMultiple(String masterTableName, String servantTableName, String masterColumn, String servantColumn) {
        return new Multiple(masterTableName, servantTableName, masterColumn, servantColumn);
    }
}