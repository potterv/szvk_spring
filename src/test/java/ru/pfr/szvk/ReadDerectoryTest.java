package ru.pfr.szvk;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import ru.pfr.szvk.readwritefiles.ReadDerectory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.CASE_INSENSITIVE_ORDER;
import static org.junit.Assert.*;

public class ReadDerectoryTest {

    @Test
    public void getListFiles() {
//        Тестовый  путь для проверки метода

        String pathD = String.join("",new File("").getAbsolutePath(),"\\mail\\inSZVK");
//        System.out.println(pathD);
        ReadDerectory rf= ReadDerectory.getInstance();

        for (StringBuffer lf:rf.getListFiles(pathD)
             ) {
            System.out.println(lf);
        }

//        List<StringBuffer> lf = new LinkedList<StringBuffer>();
//        lf.add(new StringBuffer("mail\\inSZVK\\PFR-700-Y-2019-ORG-092-001-000001-DCK-00020-DPT-000000-DCK-00000.XML"));
//        lf.add(new StringBuffer("D:\\IdeaProject\\szvk\\mail\\R92_REG_ANSI.csv"));
//        lf.add(new StringBuffer("D:\\IdeaProject\\szvk\\mail\\R92_REG_ANSI1.csv"));
//        lf.sort(new StringBuffer((CharSequence) CASE_INSENSITIVE_ORDER));
//        Assert.assertEquals(lf.toString(), rf.getListFiles(pathD).toString());
    }

    @Test
    public void setSZVKFiles() {
    }

    @Test
    public void getPathInSZVM() {
        System.out.println(LocalDate.now().toString());

    }
}