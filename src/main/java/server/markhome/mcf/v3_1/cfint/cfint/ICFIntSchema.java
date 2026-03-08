// Description: Java 25 interface for a CFInt schema.

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfint;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.math.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.util.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import java.util.concurrent.atomic.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public interface ICFIntSchema
extends ICFSecSchema
{
	public static final String SCHEMA_NAME = "CFInt";
	public static final String DBSCHEMA_NAME = "CFInt31";
	static final AtomicReference<ApplicationContext> arApplicationContext = new AtomicReference<>();

	public default void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		arApplicationContext.compareAndSet(arApplicationContext.get(), applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return( arApplicationContext.get() );
	}

		final static ArrayList<ICFSecSchema.ClassMapEntry> entries = new ArrayList<>();
		final static HashMap<Integer,ICFSecSchema.ClassMapEntry> mapBackingClassCodeToEntry = new HashMap<>();
		final static HashMap<Integer,ICFSecSchema.ClassMapEntry> mapRuntimeClassCodeToEntry = new HashMap<>();
		final static AtomicReference<ICFIntSchema> backingCFInt = new AtomicReference<>();
		public static ICFIntSchema getBackingCFInt() {
			return( ICFIntSchema.backingCFInt.get() );
		}
		
		public static void setBackingCFInt(ICFIntSchema backingSchema) {
			ICFIntSchema.backingCFInt.set(backingSchema);
		}
		
		public ICFIntSchema getCFIntSchema();
		public void setCFIntSchema(ICFIntSchema schema);
		
		public static int doInitClassMapEntries(int value) {
			value = ICFSecSchema.doInitClassMapEntries(value);
			if (ICFIntSchema.entries.isEmpty()) {
				ICFSecSchema.ClassMapEntry entry;
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "License", ICFIntLicense.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "MajorVersion", ICFIntMajorVersion.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "MimeType", ICFIntMimeType.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "MinorVersion", ICFIntMinorVersion.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "SubProject", ICFIntSubProject.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "Tld", ICFIntTld.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "TopDomain", ICFIntTopDomain.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "TopProject", ICFIntTopProject.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				entry = new ICFSecSchema.ClassMapEntry(ICFIntSchema.SCHEMA_NAME, "URLProtocol", ICFIntURLProtocol.CLASS_CODE);
				ICFIntSchema.entries.add(entry);
				for( ICFSecSchema.ClassMapEntry cur: ICFIntSchema.entries) {
					cur.setRuntimeClassCode(value++);
				}
				ICFIntSchema.mapBackingClassCodeToEntry.clear();
				ICFIntSchema.mapRuntimeClassCodeToEntry.clear();
				for( ICFSecSchema.ClassMapEntry cur: ICFIntSchema.entries) {
					ICFIntSchema.mapBackingClassCodeToEntry.put(cur.getBackingClassCode(), cur);
					ICFIntSchema.mapRuntimeClassCodeToEntry.put(cur.getRuntimeClassCode(), cur);
				}
			}
			return(value);
		}
		
		public static ICFSecSchema.ClassMapEntry getClassMapByBackingClassCode(int code) {
			ICFSecSchema.ClassMapEntry entry;
			entry = ICFSecSchema.mapBackingClassCodeToEntry.get(code);
			if (entry != null) {
				return( entry );
			}
			entry = ICFIntSchema.mapBackingClassCodeToEntry.get(code);
			if (entry != null) {
				return( entry );
			}
			return( null );
		}
		
		public static ICFSecSchema.ClassMapEntry getClassMapByRuntimeClassCode(int code) {
			ICFSecSchema.ClassMapEntry entry;
			entry = ICFSecSchema.mapRuntimeClassCodeToEntry.get(code);
			if (entry != null) {
				return( entry );
			}
			entry = ICFIntSchema.mapRuntimeClassCodeToEntry.get(code);
			if (entry != null) {
				return( entry );
			}
			return( null );
		}
		
		public int initClassMapEntries(int value);
		public void wireRecConstructors();
		public void wireTableTableInstances();

	/**
	 *	Allocate a new schema instance.
	 *
	 *	@return	A new ICFSecSchema instance.
	 */
	public ICFSecSchema newSchema();

	/**
	 *	Get the next ISOCcyIdGen identifier.
	 *
	 *	@return	The next ISOCcyIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public short nextISOCcyIdGen();

	/**
	 *	Get the next ISOCtryIdGen identifier.
	 *
	 *	@return	The next ISOCtryIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public short nextISOCtryIdGen();

	/**
	 *	Get the next ISOLangIdGen identifier.
	 *
	 *	@return	The next ISOLangIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public short nextISOLangIdGen();

	/**
	 *	Get the next ISOTZoneIdGen identifier.
	 *
	 *	@return	The next ISOTZoneIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public short nextISOTZoneIdGen();

	/**
	 *	Get the next MimeTypeIdGen identifier.
	 *
	 *	@return	The next MimeTypeIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public int nextMimeTypeIdGen();

	/**
	 *	Get the next URLProtocolIdGen identifier.
	 *
	 *	@return	The next URLProtocolIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public int nextURLProtocolIdGen();

	/**
	 *	Get the next ClusterIdGen identifier.
	 *
	 *	@return	The next ClusterIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextClusterIdGen();

	/**
	 *	Get the next SecSessionIdGen identifier.
	 *
	 *	@return	The next SecSessionIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSecSessionIdGen();

	/**
	 *	Get the next SecUserIdGen identifier.
	 *
	 *	@return	The next SecUserIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSecUserIdGen();

	/**
	 *	Get the next ServiceTypeIdGen identifier.
	 *
	 *	@return	The next ServiceTypeIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextServiceTypeIdGen();

	/**
	 *	Get the next TenantIdGen identifier.
	 *
	 *	@return	The next TenantIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTenantIdGen();

	/**
	 *	Get the next HostNodeIdGen identifier.
	 *
	 *	@return	The next HostNodeIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextHostNodeIdGen();

	/**
	 *	Get the next SecGroupIdGen identifier.
	 *
	 *	@return	The next SecGroupIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSecGroupIdGen();

	/**
	 *	Get the next SecGrpIncIdGen identifier.
	 *
	 *	@return	The next SecGrpIncIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSecGrpIncIdGen();

	/**
	 *	Get the next SecGrpMembIdGen identifier.
	 *
	 *	@return	The next SecGrpMembIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSecGrpMembIdGen();

	/**
	 *	Get the next ServiceIdGen identifier.
	 *
	 *	@return	The next ServiceIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextServiceIdGen();

	/**
	 *	Get the next TSecGroupIdGen identifier.
	 *
	 *	@return	The next TSecGroupIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTSecGroupIdGen();

	/**
	 *	Get the next TSecGrpIncIdGen identifier.
	 *
	 *	@return	The next TSecGrpIncIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTSecGrpIncIdGen();

	/**
	 *	Get the next TSecGrpMembIdGen identifier.
	 *
	 *	@return	The next TSecGrpMembIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTSecGrpMembIdGen();

	/**
	 *	Get the next MajorVersionIdGen identifier.
	 *
	 *	@return	The next MajorVersionIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextMajorVersionIdGen();

	/**
	 *	Get the next MinorVersionIdGen identifier.
	 *
	 *	@return	The next MinorVersionIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextMinorVersionIdGen();

	/**
	 *	Get the next SubProjectIdGen identifier.
	 *
	 *	@return	The next SubProjectIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextSubProjectIdGen();

	/**
	 *	Get the next TldIdGen identifier.
	 *
	 *	@return	The next TldIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTldIdGen();

	/**
	 *	Get the next TopDomainIdGen identifier.
	 *
	 *	@return	The next TopDomainIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTopDomainIdGen();

	/**
	 *	Get the next TopProjectIdGen identifier.
	 *
	 *	@return	The next TopProjectIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextTopProjectIdGen();

	/**
	 *	Get the next LicenseIdGen identifier.
	 *
	 *	@return	The next LicenseIdGen identifier.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFLibDbKeyHash256 nextLicenseIdGen();

	/**
	 *	Get the Cluster Table interface for the schema.
	 *
	 *	@return	The Cluster Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecClusterTable getTableCluster();

	/**
	 *	Get the Cluster Factory interface for the schema.
	 *
	 *	@return	The Cluster Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecClusterFactory getFactoryCluster();

	/**
	 *	Get the HostNode Table interface for the schema.
	 *
	 *	@return	The HostNode Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecHostNodeTable getTableHostNode();

	/**
	 *	Get the HostNode Factory interface for the schema.
	 *
	 *	@return	The HostNode Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecHostNodeFactory getFactoryHostNode();

	/**
	 *	Get the ISOCcy Table interface for the schema.
	 *
	 *	@return	The ISOCcy Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCcyTable getTableISOCcy();

	/**
	 *	Get the ISOCcy Factory interface for the schema.
	 *
	 *	@return	The ISOCcy Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCcyFactory getFactoryISOCcy();

	/**
	 *	Get the ISOCtry Table interface for the schema.
	 *
	 *	@return	The ISOCtry Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryTable getTableISOCtry();

	/**
	 *	Get the ISOCtry Factory interface for the schema.
	 *
	 *	@return	The ISOCtry Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryFactory getFactoryISOCtry();

	/**
	 *	Get the ISOCtryCcy Table interface for the schema.
	 *
	 *	@return	The ISOCtryCcy Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryCcyTable getTableISOCtryCcy();

	/**
	 *	Get the ISOCtryCcy Factory interface for the schema.
	 *
	 *	@return	The ISOCtryCcy Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryCcyFactory getFactoryISOCtryCcy();

	/**
	 *	Get the ISOCtryLang Table interface for the schema.
	 *
	 *	@return	The ISOCtryLang Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryLangTable getTableISOCtryLang();

	/**
	 *	Get the ISOCtryLang Factory interface for the schema.
	 *
	 *	@return	The ISOCtryLang Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOCtryLangFactory getFactoryISOCtryLang();

	/**
	 *	Get the ISOLang Table interface for the schema.
	 *
	 *	@return	The ISOLang Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOLangTable getTableISOLang();

	/**
	 *	Get the ISOLang Factory interface for the schema.
	 *
	 *	@return	The ISOLang Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOLangFactory getFactoryISOLang();

	/**
	 *	Get the ISOTZone Table interface for the schema.
	 *
	 *	@return	The ISOTZone Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOTZoneTable getTableISOTZone();

	/**
	 *	Get the ISOTZone Factory interface for the schema.
	 *
	 *	@return	The ISOTZone Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecISOTZoneFactory getFactoryISOTZone();

	/**
	 *	Get the License Table interface for the schema.
	 *
	 *	@return	The License Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntLicenseTable getTableLicense();

	/**
	 *	Get the License Factory interface for the schema.
	 *
	 *	@return	The License Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntLicenseFactory getFactoryLicense();

	/**
	 *	Get the MajorVersion Table interface for the schema.
	 *
	 *	@return	The MajorVersion Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMajorVersionTable getTableMajorVersion();

	/**
	 *	Get the MajorVersion Factory interface for the schema.
	 *
	 *	@return	The MajorVersion Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMajorVersionFactory getFactoryMajorVersion();

	/**
	 *	Get the MimeType Table interface for the schema.
	 *
	 *	@return	The MimeType Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMimeTypeTable getTableMimeType();

	/**
	 *	Get the MimeType Factory interface for the schema.
	 *
	 *	@return	The MimeType Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMimeTypeFactory getFactoryMimeType();

	/**
	 *	Get the MinorVersion Table interface for the schema.
	 *
	 *	@return	The MinorVersion Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMinorVersionTable getTableMinorVersion();

	/**
	 *	Get the MinorVersion Factory interface for the schema.
	 *
	 *	@return	The MinorVersion Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntMinorVersionFactory getFactoryMinorVersion();

	/**
	 *	Get the SecDevice Table interface for the schema.
	 *
	 *	@return	The SecDevice Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecDeviceTable getTableSecDevice();

	/**
	 *	Get the SecDevice Factory interface for the schema.
	 *
	 *	@return	The SecDevice Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecDeviceFactory getFactorySecDevice();

	/**
	 *	Get the SecGroup Table interface for the schema.
	 *
	 *	@return	The SecGroup Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGroupTable getTableSecGroup();

	/**
	 *	Get the SecGroup Factory interface for the schema.
	 *
	 *	@return	The SecGroup Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGroupFactory getFactorySecGroup();

	/**
	 *	Get the SecGrpInc Table interface for the schema.
	 *
	 *	@return	The SecGrpInc Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGrpIncTable getTableSecGrpInc();

	/**
	 *	Get the SecGrpInc Factory interface for the schema.
	 *
	 *	@return	The SecGrpInc Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGrpIncFactory getFactorySecGrpInc();

	/**
	 *	Get the SecGrpMemb Table interface for the schema.
	 *
	 *	@return	The SecGrpMemb Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGrpMembTable getTableSecGrpMemb();

	/**
	 *	Get the SecGrpMemb Factory interface for the schema.
	 *
	 *	@return	The SecGrpMemb Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecGrpMembFactory getFactorySecGrpMemb();

	/**
	 *	Get the SecSession Table interface for the schema.
	 *
	 *	@return	The SecSession Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecSessionTable getTableSecSession();

	/**
	 *	Get the SecSession Factory interface for the schema.
	 *
	 *	@return	The SecSession Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecSessionFactory getFactorySecSession();

	/**
	 *	Get the SecUser Table interface for the schema.
	 *
	 *	@return	The SecUser Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecUserTable getTableSecUser();

	/**
	 *	Get the SecUser Factory interface for the schema.
	 *
	 *	@return	The SecUser Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSecUserFactory getFactorySecUser();

	/**
	 *	Get the Service Table interface for the schema.
	 *
	 *	@return	The Service Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecServiceTable getTableService();

	/**
	 *	Get the Service Factory interface for the schema.
	 *
	 *	@return	The Service Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecServiceFactory getFactoryService();

	/**
	 *	Get the ServiceType Table interface for the schema.
	 *
	 *	@return	The ServiceType Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecServiceTypeTable getTableServiceType();

	/**
	 *	Get the ServiceType Factory interface for the schema.
	 *
	 *	@return	The ServiceType Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecServiceTypeFactory getFactoryServiceType();

	/**
	 *	Get the SubProject Table interface for the schema.
	 *
	 *	@return	The SubProject Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntSubProjectTable getTableSubProject();

	/**
	 *	Get the SubProject Factory interface for the schema.
	 *
	 *	@return	The SubProject Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntSubProjectFactory getFactorySubProject();

	/**
	 *	Get the SysCluster Table interface for the schema.
	 *
	 *	@return	The SysCluster Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSysClusterTable getTableSysCluster();

	/**
	 *	Get the SysCluster Factory interface for the schema.
	 *
	 *	@return	The SysCluster Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecSysClusterFactory getFactorySysCluster();

	/**
	 *	Get the TSecGroup Table interface for the schema.
	 *
	 *	@return	The TSecGroup Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGroupTable getTableTSecGroup();

	/**
	 *	Get the TSecGroup Factory interface for the schema.
	 *
	 *	@return	The TSecGroup Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGroupFactory getFactoryTSecGroup();

	/**
	 *	Get the TSecGrpInc Table interface for the schema.
	 *
	 *	@return	The TSecGrpInc Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGrpIncTable getTableTSecGrpInc();

	/**
	 *	Get the TSecGrpInc Factory interface for the schema.
	 *
	 *	@return	The TSecGrpInc Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGrpIncFactory getFactoryTSecGrpInc();

	/**
	 *	Get the TSecGrpMemb Table interface for the schema.
	 *
	 *	@return	The TSecGrpMemb Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGrpMembTable getTableTSecGrpMemb();

	/**
	 *	Get the TSecGrpMemb Factory interface for the schema.
	 *
	 *	@return	The TSecGrpMemb Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTSecGrpMembFactory getFactoryTSecGrpMemb();

	/**
	 *	Get the Tenant Table interface for the schema.
	 *
	 *	@return	The Tenant Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTenantTable getTableTenant();

	/**
	 *	Get the Tenant Factory interface for the schema.
	 *
	 *	@return	The Tenant Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFSecTenantFactory getFactoryTenant();

	/**
	 *	Get the Tld Table interface for the schema.
	 *
	 *	@return	The Tld Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTldTable getTableTld();

	/**
	 *	Get the Tld Factory interface for the schema.
	 *
	 *	@return	The Tld Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTldFactory getFactoryTld();

	/**
	 *	Get the TopDomain Table interface for the schema.
	 *
	 *	@return	The TopDomain Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTopDomainTable getTableTopDomain();

	/**
	 *	Get the TopDomain Factory interface for the schema.
	 *
	 *	@return	The TopDomain Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTopDomainFactory getFactoryTopDomain();

	/**
	 *	Get the TopProject Table interface for the schema.
	 *
	 *	@return	The TopProject Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTopProjectTable getTableTopProject();

	/**
	 *	Get the TopProject Factory interface for the schema.
	 *
	 *	@return	The TopProject Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntTopProjectFactory getFactoryTopProject();

	/**
	 *	Get the URLProtocol Table interface for the schema.
	 *
	 *	@return	The URLProtocol Table interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntURLProtocolTable getTableURLProtocol();

	/**
	 *	Get the URLProtocol Factory interface for the schema.
	 *
	 *	@return	The URLProtocol Factory interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	public ICFIntURLProtocolFactory getFactoryURLProtocol();

	/**
	 *	Get the Table Permissions interface for the schema.
	 *
	 *	@return	The Table Permissions interface for the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	//public static ICFSecTablePerms getTablePerms();

	/**
	 *	Get the Table Permissions interface cast to this schema's implementation.
	 *
	 *	@return The Table Permissions interface for this schema.
	 */
	//public static ICFSecTablePerms getCFSecTablePerms();

	/**
	 *	Set the Table Permissions interface for the schema.  All fractal subclasses of
	 *	the ICFSecTablePerms implement at least that interface plus their own
	 *	accessors.
	 *
	 *	@param	value	The Table Permissions interface to be used by the schema.
	 *
	 *	@throws CFLibNotSupportedException thrown by client-side implementations.
	 */
	//public static void setTablePerms( ICFSecTablePerms value );

	public void bootstrapSchema();
}
