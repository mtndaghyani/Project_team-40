package model;

import model.DiscountCode;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DiscountCodeTest {
    @Test
    public void testDateValidation() throws ParseException {
        Date start = new SimpleDateFormat("yyyy-mm-dd").parse("2020-03-25");
        Date end = new SimpleDateFormat("yyyy-mm-dd").parse("2020-04-25");
        DiscountCode code = new DiscountCode(start, end, 10, 1000, 2);
        Assert.assertFalse(code.checkIfExpired());
    }
}
