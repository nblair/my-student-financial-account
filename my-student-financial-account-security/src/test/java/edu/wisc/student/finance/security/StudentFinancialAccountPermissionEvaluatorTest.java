/**
 * 
 */
package edu.wisc.student.finance.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import edu.wisc.student.finance.AuthorizedUserService;
import edu.wisc.uwss.UWUserDetails;

/**
 * Tests for {@link StudentFinancialAccountPermissionEvaluator}.
 * 
 * @author Nicholas Blair
 */
@RunWith(MockitoJUnitRunner.class)public class StudentFinancialAccountPermissionEvaluatorTest {
	@Mock AuthorizedUserService authzService;
	@InjectMocks StudentFinancialAccountPermissionEvaluator evaluator = new StudentFinancialAccountPermissionEvaluator();
	
	@Before
	public void setup(){
		when(authzService.getAuthorizedUsers()).thenReturn(Collections.emptyList());
	}
  /**
   * Control experiment for {@link StudentFinancialAccountPermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, Object, Object)}
   * confirming access is granted for viewCharges on the authenticated user's PVI.
   */
  @Test
  public void hasPermission_successful_viewCharges_self() {
    Authentication authentication = forPvi("UW123X123");
    assertTrue(evaluator.hasPermission(authentication, "UW123X123", StudentFinancialAccountPermissionEvaluator.VIEW_CHARGES));
  }
  /**
   * Experiment for {@link StudentFinancialAccountPermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, Object, Object)}
   * confirming access is not granted for viewCharges on a PVI that differs authenticated user's.
   */
  @Test
  public void hasPermission_failed_viewCharges_someoneelse() {
    Authentication authentication = forPvi("UW123X124");
    assertFalse(evaluator.hasPermission(authentication, "UW123X123", StudentFinancialAccountPermissionEvaluator.VIEW_CHARGES));
  }
  
  /**
   * Mock an {@link Authentication} for a {@link UWUserDetails}.
   * 
   * @param pvi the value for {@link UWUserDetails#getPvi()}
   * @return the mocked {@link Authentication}.
   */
  protected Authentication forPvi(String pvi) {
    Authentication authentication = mock(Authentication.class);
    UWUserDetails user = mock(UWUserDetails.class);
    when(authentication.getPrincipal()).thenReturn(user);
    when(user.getPvi()).thenReturn(pvi);
    
    return authentication;
  }
}
