package fairman.aidan.rental.tool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fairman.aidan.rental.tool.errors.DiscountOutOfRangeException;
import fairman.aidan.rental.tool.errors.RentalDaysRangeException;
import fairman.aidan.rental.tool.model.ToolAgreement;
import fairman.aidan.rental.tool.service.ToolAgreementService;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolRentalApplicationTests {

  @Autowired
  private ToolAgreementService service;

  @Test
  void contextLoads() {
  }

  // Tests for the provided cases

  @Test
  public void testCase1() {
    final String TOOL_CODE = "JAKR";
    final LocalDate DATE = LocalDate.of(2015, 9, 3);
    final int DISCOUNT = 101;
    final int DAYS = 5;
    assertThrows(DiscountOutOfRangeException.class, () ->
        service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE));
  }

  @Test
  public void testCase2()
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    final String TOOL_CODE = "LADW";
    final LocalDate DATE = LocalDate.of(2020, 7, 2);
    final int DISCOUNT = 10;
    final int DAYS = 3;
    final int CHARGE_DAYS = 2;
    final double PRE_DISCOUNT = 3.98;
    final double DISCOUNT_AMOUNT = 0.40;
    final double TOTAL = 3.58;
    ToolAgreement toolAgreement = service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
    assertEquals(CHARGE_DAYS, toolAgreement.getToolCharge().getChargeableDays());
    assertEquals(TOTAL, toolAgreement.getToolCharge().getCharge());
    assertEquals(PRE_DISCOUNT, toolAgreement.getToolCharge().getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, toolAgreement.getToolCharge().getDiscountAmount());
  }

  @Test
  public void testCase3()
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    final String TOOL_CODE = "CHNS";
    final LocalDate DATE = LocalDate.of(2015, 7, 2);
    final int DISCOUNT = 25;
    final int DAYS = 5;
    final int CHARGE_DAYS = 3;
    final double PRE_DISCOUNT = 4.47;
    final double DISCOUNT_AMOUNT = 1.12;
    final double TOTAL = 3.35;
    ToolAgreement toolAgreement = service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
    assertEquals(CHARGE_DAYS, toolAgreement.getToolCharge().getChargeableDays());
    assertEquals(TOTAL, toolAgreement.getToolCharge().getCharge());
    assertEquals(PRE_DISCOUNT, toolAgreement.getToolCharge().getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, toolAgreement.getToolCharge().getDiscountAmount());
  }

  @Test
  public void testCase4()
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    final String TOOL_CODE = "JAKD";
    final LocalDate DATE = LocalDate.of(2015, 9, 3);
    final int DISCOUNT = 0;
    final int DAYS = 6;
    final int CHARGE_DAYS = 3;
    final double PRE_DISCOUNT = 8.97;
    final double DISCOUNT_AMOUNT = 0.0;
    final double TOTAL = 8.97;
    ToolAgreement toolAgreement = service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
    assertEquals(CHARGE_DAYS, toolAgreement.getToolCharge().getChargeableDays());
    assertEquals(TOTAL, toolAgreement.getToolCharge().getCharge());
    assertEquals(PRE_DISCOUNT, toolAgreement.getToolCharge().getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, toolAgreement.getToolCharge().getDiscountAmount());
  }

  @Test
  public void testCase5()
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    final String TOOL_CODE = "JAKR";
    final LocalDate DATE = LocalDate.of(2015, 7, 2);
    final int DISCOUNT = 0;
    final int DAYS = 9;
    final int CHARGE_DAYS = 6;
    final double PRE_DISCOUNT = 17.94;
    final double DISCOUNT_AMOUNT = 0.0;
    final double TOTAL = 17.94;
    ToolAgreement toolAgreement = service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
    assertEquals(CHARGE_DAYS, toolAgreement.getToolCharge().getChargeableDays());
    assertEquals(TOTAL, toolAgreement.getToolCharge().getCharge());
    assertEquals(PRE_DISCOUNT, toolAgreement.getToolCharge().getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, toolAgreement.getToolCharge().getDiscountAmount());
  }

  @Test
  public void testCase6()
      throws DiscountOutOfRangeException, RentalDaysRangeException, SQLException {
    final String TOOL_CODE = "JAKR";
    final LocalDate DATE = LocalDate.of(2020, 7, 2);
    final int DISCOUNT = 50;
    final int DAYS = 4;
    final int CHARGE_DAYS = 1;
    final double PRE_DISCOUNT = 2.99;
    final double DISCOUNT_AMOUNT = 1.50;
    final double TOTAL = 1.49;
    ToolAgreement toolAgreement = service.generateToolAgreement(TOOL_CODE, DAYS, DISCOUNT, DATE);
    assertNotNull(toolAgreement);
    assertEquals(CHARGE_DAYS, toolAgreement.getToolCharge().getChargeableDays());
    assertEquals(TOTAL, toolAgreement.getToolCharge().getCharge());
    assertEquals(PRE_DISCOUNT, toolAgreement.getToolCharge().getPreDiscountCharge());
    assertEquals(DISCOUNT_AMOUNT, toolAgreement.getToolCharge().getDiscountAmount());
  }
}
