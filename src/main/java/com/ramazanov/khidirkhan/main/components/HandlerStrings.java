package com.ramazanov.khidirkhan.main.components;

import com.ramazanov.khidirkhan.main.Application;
import com.ramazanov.khidirkhan.main.exceptions.AddExitingWordException;
import com.ramazanov.khidirkhan.main.exceptions.NotValidStringException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Хидир on 14.02.2017.
 */
public class HandlerStrings {
    private Writer writer;

    public HandlerStrings(Writer dataManager){
        this.writer = dataManager;
    }

    /**
     * Проверяем, что строка содержит только разрешенные
     * символы (русские буквы, цифры, знаки препинания)
     *
     * @param str передаем строку для проверки
     * @return true если строка не содержит запрещенных символов, false в случае, если содержит
     */
    private boolean validate(String str) {
        return str.matches("[а-яА-Я0-9ёЁ\\s\\.,;:\\?!-]+");
    }


    private boolean isUrl(String str){
        return str.matches("[-a-zA-Z0-9@:%_\\+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(\\/[-a-zA-Z0-9@:%_\\+.~#?&//=]*)?");
    }

    /**
     * Разбиваем строку на массив символов (игнорируя знаки препинания)
     *
     * @param line передаем строку, которую будем разбивать
     * @return ArrayList<String> со словами из переданной строки.
     */
    private ArrayList<String> splitLine(String line){
        Pattern p = Pattern.compile("\\b[а-яА-Я0-9-]{2,}");
        Matcher m = p.matcher(line);

        ArrayList<String> ArrayMain = new ArrayList<>();
        int i = 0;
        while(m.find()){
            ArrayMain.add(m.group());
            //System.out.println("--- " + m.group());
        }
        return ArrayMain;
    }

    /**
     * Проверяет переданную строку на валидность, если строка не валидна кидает исключение NotValidStringException.
     * Затем разбивает строку и по слову добавляет в DataSet, в случае появления дубликата в наборе кидает исключение AddExitingWordException
     *
     * @param line строка из ресурса
     * @throws NotValidStringException
     * @throws AddExitingWordException
     */
    public void handleLine(String line) throws NotValidStringException, AddExitingWordException {
            //Проверяем наличие запрещенных символов в считанной строке
            if (!validate(line))
                throw new NotValidStringException();

        ArrayList<String> words = splitLine(line);
        for(String word:words){
            System.out.println(word+" name:"+Thread.currentThread().getName());
            writer.setWord(word);
        }
    }
}
