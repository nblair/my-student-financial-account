/**
 *
 */
package edu.wisc.student.finance.demo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import edu.wisc.student.finance.AuthorizedUserDao;
import edu.wisc.student.finance.v1.AuthorizedUser;

/**
 * Demo implementation of {@link AuthorizedUserDao}.
 * 
 * @author Collin Cudd
 *
 */
@Named
public class DemoAuthorizedUsersDaoImpl extends GenericDemoDataDao<AuthorizedUser> implements
    AuthorizedUserDao {
  
  public DemoAuthorizedUsersDaoImpl() {
    super(AuthorizedUser.class);
  }

  List<AuthorizedUser> demoData = new ArrayList<AuthorizedUser>();
  public static final String DEMO_AUTHZ_USERS_DATA =
      "edu/wisc/student/finance/demo/demo-authorized-users-service-data.json";

  /*
   * (non-Javadoc)
   * 
   * @see edu.wisc.student.finance.AuthorizedUserDao#getAuthorizedUsers(edu.wisc.uwss.UWUserDetails)
   */
  @Override
  public List<AuthorizedUser> getAuthorizedUsers(String pvi) {
    return  getDemoData(pvi);
  }

  @Override
  public void addAuthorizedUser(String pvi, AuthorizedUser authorizedUser) {
    System.out.println(pvi + " is authorizing " + authorizedUser); // FIXME: delete or turn into
                                                                   // sanitized log statement
    this.data.get(pvi).add(authorizedUser);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.wisc.student.finance.demo.GenericDemoDataDao#getDemoDataPath()
   */
  @Override
  public String getDemoDataPath() {
    return DEMO_AUTHZ_USERS_DATA;
  }
}
