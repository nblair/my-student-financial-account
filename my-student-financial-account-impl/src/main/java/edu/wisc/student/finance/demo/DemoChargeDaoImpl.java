/**
 * 
 */
package edu.wisc.student.finance.demo;

import java.util.Collection;

import javax.inject.Named;

import edu.wisc.student.finance.ChargeDao;
import edu.wisc.student.finance.v1.ChargeType;

/**
 * Demo implementation of {@link ChargeDao} backed by {@link GenericDemoDataDao}.
 * 
 * @author Nicholas Blair
 */
@Named
public class DemoChargeDaoImpl extends GenericDemoDataDao<ChargeType> implements ChargeDao {
	
	/**
   * @param typeParameterClass
   */
  public DemoChargeDaoImpl() {
    super(ChargeType.class);
  }

  public static final String DEMO_CHARGE_DATA = "edu/wisc/student/finance/demo/demo-charge-service-data.json";
  
  /*
   * (non-Javadoc)
   * @see edu.wisc.student.finance.ChargeDao#getCharges(java.lang.String)
   */
  @Override
  public Collection<ChargeType> getCharges(String studentIdentifier) {
    return getDemoData(studentIdentifier);
  }

	/* (non-Javadoc)
	 * @see edu.wisc.student.finance.demo.GenericDemoDataDao#getDemoDataPath()
	 */
	@Override
	public String getDemoDataPath() {
		return DEMO_CHARGE_DATA;
	}
}
