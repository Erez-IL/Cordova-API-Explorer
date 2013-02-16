package com.emitrom.touch4j.app.client.data;

import java.util.ArrayList;
import java.util.List;

import com.emitrom.touch4j.client.ui.SelectOption;

/**
 * Utility class for creating basemodel data used in the app
 */
public class DataUtil {

    private DataUtil() {

    }

    public static List<SelectOption> getEncodings() {
        List<SelectOption> selectOptions = new ArrayList<SelectOption>();
        selectOptions.add(new SelectOption("PNG", "1"));
        selectOptions.add(new SelectOption("JPG", "0"));
        return selectOptions;
    }

    public static List<SelectOption> getSources() {
        List<SelectOption> selectOptions = new ArrayList<SelectOption>();
        selectOptions.add(new SelectOption("Saved Photo Album", "2"));
        selectOptions.add(new SelectOption("Camera", "1"));
        selectOptions.add(new SelectOption("Photo Library", "0"));
        return selectOptions;
    }

    public static List<SelectOption> getQualities() {
        List<SelectOption> selectOptions = new ArrayList<SelectOption>();
        selectOptions.add(new SelectOption("0", "0"));
        selectOptions.add(new SelectOption("25", "25"));
        selectOptions.add(new SelectOption("50", "50"));
        selectOptions.add(new SelectOption("75", "75"));
        selectOptions.add(new SelectOption("100", "100"));
        return selectOptions;
    }
}
