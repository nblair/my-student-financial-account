/**
 *
 */
package edu.wisc.student.finance.demo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
  protected final Log log = LogFactory.getLog(this.getClass()); 
  List<AuthorizedUser> demoData = new ArrayList<AuthorizedUser>();
  public static final String DEMO_AUTHZ_USERS_DATA =
      "edu/wisc/student/finance/demo/demo-authorized-users-service-data.json";
  /**
   * Constructor passes the same type as T.
   * @see GenericDemoDataDao#GenericDemoDataDao(Class)
   */
  public DemoAuthorizedUsersDaoImpl() {
    super(AuthorizedUser.class);
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.wisc.student.finance.AuthorizedUserDao#getAuthorizedUsers(edu.wisc.uwss.UWUserDetails)
   */
  @Override
  public List<AuthorizedUser> getAuthorizedUsers(String pvi) {
    return  getDemoData(pvi);
  }
  /* (non-Javadoc)
   * @see edu.wisc.student.finance.AuthorizedUserDao#addAuthorizedUser(java.lang.String, edu.wisc.student.finance.v1.AuthorizedUser)
   */
  @Override
  public void addAuthorizedUser(String pvi, AuthorizedUser authorizedUser) {
    log.info(pvi + " is authorizing " + authorizedUser);
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
