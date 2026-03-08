// Description: Java 25 Schema Object implementation for CFInt.

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

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;

public class CFIntSchemaObj
	implements ICFIntSchemaObj
{
	public static String SCHEMA_NAME = "CFInt";
	public static String SCHEMA_DBNAME = "CFInt31";
	protected ICFSecAuthorization authorization = null;
	protected String secClusterName = "system";
	protected String secTenantName = "system";
	protected String secUserName = "system";
	protected ICFSecClusterObj secCluster = null;
	protected CFLibDbKeyHash256 secClusterId = null;
	protected ICFSecTenantObj secTenant = null;
	protected CFLibDbKeyHash256 secTenantId = null;
	protected ICFSecSecUserObj secUser = null;
	protected CFLibDbKeyHash256 secSessionUserId = null;
	protected ICFSecSecSessionObj secSession = null;
	protected CFLibDbKeyHash256 secSessionSessionId = null;
	protected String schemaDbName = SCHEMA_DBNAME;
	protected String lowerDbSchemaName = SCHEMA_DBNAME.toLowerCase();

	protected ICFSecSchema cfsecBackingStore;
	protected ICFIntSchema cfintBackingStore;
	protected ICFIntClusterTableObj clusterTableObj;
	protected ICFIntHostNodeTableObj hostNodeTableObj;
	protected ICFIntISOCcyTableObj iSOCcyTableObj;
	protected ICFIntISOCtryTableObj iSOCtryTableObj;
	protected ICFIntISOCtryCcyTableObj iSOCtryCcyTableObj;
	protected ICFIntISOCtryLangTableObj iSOCtryLangTableObj;
	protected ICFIntISOLangTableObj iSOLangTableObj;
	protected ICFIntISOTZoneTableObj iSOTZoneTableObj;
	protected ICFIntLicenseTableObj licenseTableObj;
	protected ICFIntMajorVersionTableObj majorVersionTableObj;
	protected ICFIntMimeTypeTableObj mimeTypeTableObj;
	protected ICFIntMinorVersionTableObj minorVersionTableObj;
	protected ICFIntSecDeviceTableObj secDeviceTableObj;
	protected ICFIntSecGroupTableObj secGroupTableObj;
	protected ICFIntSecGrpIncTableObj secGrpIncTableObj;
	protected ICFIntSecGrpMembTableObj secGrpMembTableObj;
	protected ICFIntSecSessionTableObj secSessionTableObj;
	protected ICFIntSecUserTableObj secUserTableObj;
	protected ICFIntServiceTableObj serviceTableObj;
	protected ICFIntServiceTypeTableObj serviceTypeTableObj;
	protected ICFIntSubProjectTableObj subProjectTableObj;
	protected ICFIntSysClusterTableObj sysClusterTableObj;
	protected ICFIntTSecGroupTableObj tSecGroupTableObj;
	protected ICFIntTSecGrpIncTableObj tSecGrpIncTableObj;
	protected ICFIntTSecGrpMembTableObj tSecGrpMembTableObj;
	protected ICFIntTenantTableObj tenantTableObj;
	protected ICFIntTldTableObj tldTableObj;
	protected ICFIntTopDomainTableObj topDomainTableObj;
	protected ICFIntTopProjectTableObj topProjectTableObj;
	protected ICFIntURLProtocolTableObj uRLProtocolTableObj;

	public CFIntSchemaObj() {

		cfsecBackingStore = null;
		cfintBackingStore = null;
		clusterTableObj = new CFIntClusterTableObj( this );
		hostNodeTableObj = new CFIntHostNodeTableObj( this );
		iSOCcyTableObj = new CFIntISOCcyTableObj( this );
		iSOCtryTableObj = new CFIntISOCtryTableObj( this );
		iSOCtryCcyTableObj = new CFIntISOCtryCcyTableObj( this );
		iSOCtryLangTableObj = new CFIntISOCtryLangTableObj( this );
		iSOLangTableObj = new CFIntISOLangTableObj( this );
		iSOTZoneTableObj = new CFIntISOTZoneTableObj( this );
		licenseTableObj = new CFIntLicenseTableObj( this );
		majorVersionTableObj = new CFIntMajorVersionTableObj( this );
		mimeTypeTableObj = new CFIntMimeTypeTableObj( this );
		minorVersionTableObj = new CFIntMinorVersionTableObj( this );
		secDeviceTableObj = new CFIntSecDeviceTableObj( this );
		secGroupTableObj = new CFIntSecGroupTableObj( this );
		secGrpIncTableObj = new CFIntSecGrpIncTableObj( this );
		secGrpMembTableObj = new CFIntSecGrpMembTableObj( this );
		secSessionTableObj = new CFIntSecSessionTableObj( this );
		secUserTableObj = new CFIntSecUserTableObj( this );
		serviceTableObj = new CFIntServiceTableObj( this );
		serviceTypeTableObj = new CFIntServiceTypeTableObj( this );
		subProjectTableObj = new CFIntSubProjectTableObj( this );
		sysClusterTableObj = new CFIntSysClusterTableObj( this );
		tSecGroupTableObj = new CFIntTSecGroupTableObj( this );
		tSecGrpIncTableObj = new CFIntTSecGrpIncTableObj( this );
		tSecGrpMembTableObj = new CFIntTSecGrpMembTableObj( this );
		tenantTableObj = new CFIntTenantTableObj( this );
		tldTableObj = new CFIntTldTableObj( this );
		topDomainTableObj = new CFIntTopDomainTableObj( this );
		topProjectTableObj = new CFIntTopProjectTableObj( this );
		uRLProtocolTableObj = new CFIntURLProtocolTableObj( this );
		}

	public void setSecClusterName( String value ) {
		if( ( value == null ) || ( value.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				"setClusterName",
				1,
				"value" );
		}
		secClusterName = value;
		secCluster = null;
	}

	public String getSecClusterName() {
		return( secClusterName );
	}

	public ICFSecClusterObj getSecCluster() {
		if( secCluster == null ) {
			if( authorization != null ) {
				secCluster = getClusterTableObj().readClusterByIdIdx( authorization.getSecClusterId() );
			}
			else {
				secCluster = getClusterTableObj().readClusterByUDomNameIdx( secClusterName );
				if( secCluster == null && secClusterId != null && !secClusterId.isNull() ) {
					secCluster = getClusterTableObj().readClusterByIdIdx( secClusterId );
				}
			}
			if( secCluster != null ) {
				secClusterName = secCluster.getRequiredFullDomName();
				secClusterId = secCluster.getRequiredId();
				if( authorization != null ) {
					authorization.setSecCluster( secCluster );
				}
			}
		}
		return( secCluster );
	}

	public void setSecCluster( ICFSecClusterObj value ) {
		secCluster = value;
		if( secCluster == null ) {
			return;
		}
		secClusterId = secCluster.getRequiredId();
		secClusterName = secCluster.getRequiredFullDomName();
		if( authorization != null ) {
			authorization.setSecCluster( secCluster );
		}
	}

	public CFLibDbKeyHash256 getSecClusterId() {
		return( secClusterId );
	}

	public void setSecTenantName( String value ) {
		if( ( value == null ) || ( value.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				"setTenantName",
				1,
				"value" );
		}
		secTenantName = value;
		secTenant = null;
	}

	public String getSecTenantName() {
		return( secTenantName );
	}

	public ICFSecTenantObj getSecTenant() {
		if( secTenant == null ) {
			if( authorization != null ) {
				secTenant = getTenantTableObj().readTenantByIdIdx( authorization.getSecTenantId() );
			}
			else {
				secTenant = getTenantTableObj().readTenantByUNameIdx( getSecCluster().getRequiredId(), secTenantName );
				if( ( secTenant == null && secTenantId != null && !secTenantId.isNull() ) ) {
					secTenant = getTenantTableObj().readTenantByIdIdx( secTenantId );
				}
			}
			if( secTenant != null ) {
				secTenantName = secTenant.getRequiredTenantName();
				secTenantId = secTenant.getRequiredId();
				if( authorization != null ) {
					authorization.setSecTenant( secTenant );
				}
			}
		}
		return( secTenant );
	}

	public void setSecTenant( ICFSecTenantObj value ) {
		secTenant = value;
		if( secTenant == null ) {
			return;
		}
		secTenantId = secTenant.getRequiredId();
		secTenantName = secTenant.getRequiredTenantName();
		if( authorization != null ) {
			authorization.setSecTenant( secTenant );
		}
	}

	public CFLibDbKeyHash256 getSecTenantId() {
		return( secTenantId );
	}

	public void setSecUserName( String value ) {
		if( ( value == null ) || ( value.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				"setUserName",
				1,
				"value" );
		}
		secUserName = value;
		secUser = null;
	}

	public String getSecUserName() {
		return( secUserName );
	}

	public ICFSecSecUserObj getSecUser() {
		if( secUser == null ) {
			if( authorization != null ) {
				secUser = getSecUserTableObj().readSecUserByIdIdx( authorization.getSecUserId() );
			}
			else {
				secUser = getSecUserTableObj().readSecUserByULoginIdx( secUserName );
				if( ( secUser == null ) && ( secSessionUserId != null ) ) {
					secUser = getSecUserTableObj().readSecUserByIdIdx( secSessionUserId );
				}
			}
			if( secUser != null ) {
				secUserName = secUser.getRequiredLoginId();
				secSessionUserId = secUser.getRequiredSecUserId();
			}
		}
		return( secUser );
	}

	public void setSecUser( ICFSecSecUserObj value ) {
		secUser = value;
		if( secUser != null ) {
			secUserName = secUser.getRequiredLoginId();
			secSessionUserId = secUser.getRequiredSecUserId();
		}
	}
	public ICFSecSecSessionObj getSecSession() {
		if( secSession == null ) {
			if( authorization != null ) {
				secSession = getSecSessionTableObj().readSecSessionByIdIdx( authorization.getSecSessionId() );
			}
			else if( secSessionSessionId != null ) {
				secSession = getSecSessionTableObj().readSecSessionByIdIdx( secSessionSessionId );
			}
			if( secSession != null ) {
				secSessionSessionId = secSession.getRequiredSecSessionId();
				secSessionUserId = secSession.getRequiredSecUserId();
			}
		}
		return( secSession );
	}

	public void setSecSession( ICFSecSecSessionObj value ) {
		secSession = value;
		if( secSession == null ) {
			return;
		}
		secSessionSessionId = secSession.getRequiredSecSessionId();
		secSessionUserId = secSession.getRequiredSecUserId();
		if( authorization != null ) {
			authorization.setSecSession( secSession );
		}
	}

	public void setSecSessionId( CFLibDbKeyHash256 value ) {
		secSessionSessionId = value;
	}

	public CFLibDbKeyHash256 getSecSessionSessionId() {
		return( secSessionSessionId );
	}

	public CFLibDbKeyHash256 getSecSessionUserId() {
		return( secSessionUserId );
	}

	public ICFSecAuthorization getAuthorization() {
		return( authorization );
	}

	public void setAuthorization( ICFSecAuthorization value ) {
		authorization = value;
	}

	@Override
	public ICFSecSchema getCFSecBackingStore() {
		return( cfsecBackingStore );
	}

	@Override
	public void setCFSecBackingStore(ICFSecSchema cfsecBackingStore) {
		if (cfsecBackingStore == null) {
			throw new CFLibNullArgumentException(getClass(), "setCFSecBackingStore", 1, "cfsecBackingStore");
		}
		this.cfsecBackingStore = cfsecBackingStore;
	}

	@Override
	public ICFIntSchema getCFIntBackingStore() {
		return( cfintBackingStore );
	}

	@Override
	public void setCFIntBackingStore(ICFIntSchema cfintBackingStore) {
		if (cfintBackingStore == null) {
			throw new CFLibNullArgumentException(getClass(), "setCFIntBackingStore", 1, "cfintBackingStore");
		}
		this.cfintBackingStore = cfintBackingStore;
	}

	public String getSchemaName() {
		return( SCHEMA_NAME );
	}

	public void minimizeMemory() {
		if( clusterTableObj != null ) {
			clusterTableObj.minimizeMemory();
		}
		if( hostNodeTableObj != null ) {
			hostNodeTableObj.minimizeMemory();
		}
		if( iSOCcyTableObj != null ) {
			iSOCcyTableObj.minimizeMemory();
		}
		if( iSOCtryTableObj != null ) {
			iSOCtryTableObj.minimizeMemory();
		}
		if( iSOCtryCcyTableObj != null ) {
			iSOCtryCcyTableObj.minimizeMemory();
		}
		if( iSOCtryLangTableObj != null ) {
			iSOCtryLangTableObj.minimizeMemory();
		}
		if( iSOLangTableObj != null ) {
			iSOLangTableObj.minimizeMemory();
		}
		if( iSOTZoneTableObj != null ) {
			iSOTZoneTableObj.minimizeMemory();
		}
		if( licenseTableObj != null ) {
			licenseTableObj.minimizeMemory();
		}
		if( majorVersionTableObj != null ) {
			majorVersionTableObj.minimizeMemory();
		}
		if( mimeTypeTableObj != null ) {
			mimeTypeTableObj.minimizeMemory();
		}
		if( minorVersionTableObj != null ) {
			minorVersionTableObj.minimizeMemory();
		}
		if( secDeviceTableObj != null ) {
			secDeviceTableObj.minimizeMemory();
		}
		if( secGroupTableObj != null ) {
			secGroupTableObj.minimizeMemory();
		}
		if( secGrpIncTableObj != null ) {
			secGrpIncTableObj.minimizeMemory();
		}
		if( secGrpMembTableObj != null ) {
			secGrpMembTableObj.minimizeMemory();
		}
		if( secSessionTableObj != null ) {
			secSessionTableObj.minimizeMemory();
		}
		if( secUserTableObj != null ) {
			secUserTableObj.minimizeMemory();
		}
		if( serviceTableObj != null ) {
			serviceTableObj.minimizeMemory();
		}
		if( serviceTypeTableObj != null ) {
			serviceTypeTableObj.minimizeMemory();
		}
		if( subProjectTableObj != null ) {
			subProjectTableObj.minimizeMemory();
		}
		if( sysClusterTableObj != null ) {
			sysClusterTableObj.minimizeMemory();
		}
		if( tSecGroupTableObj != null ) {
			tSecGroupTableObj.minimizeMemory();
		}
		if( tSecGrpIncTableObj != null ) {
			tSecGrpIncTableObj.minimizeMemory();
		}
		if( tSecGrpMembTableObj != null ) {
			tSecGrpMembTableObj.minimizeMemory();
		}
		if( tenantTableObj != null ) {
			tenantTableObj.minimizeMemory();
		}
		if( tldTableObj != null ) {
			tldTableObj.minimizeMemory();
		}
		if( topDomainTableObj != null ) {
			topDomainTableObj.minimizeMemory();
		}
		if( topProjectTableObj != null ) {
			topProjectTableObj.minimizeMemory();
		}
		if( uRLProtocolTableObj != null ) {
			uRLProtocolTableObj.minimizeMemory();
		}
	}

	public void logout() {
		if( authorization == null ) {
			return;
		}
		setAuthorization( null );
		CFLibDbKeyHash256 secSessionId = authorization.getSecSessionId();
		if( secSessionId != null ) {
			ICFSecSecSessionObj secSession = getSecSessionTableObj().readSecSessionByIdIdx( secSessionId );
			if( secSession != null ) {
				if( secSession.getOptionalFinish() == null ) {
					ICFSecSecSessionEditObj editSecSession = secSession.beginEdit();
					editSecSession.setOptionalFinish( LocalDateTime.now() );
					editSecSession.update();
					editSecSession = null;
				}
			}
		}
	}

	public ICFIntClusterTableObj getClusterTableObj() {
		return( clusterTableObj );
	}

	public ICFIntHostNodeTableObj getHostNodeTableObj() {
		return( hostNodeTableObj );
	}

	public ICFIntISOCcyTableObj getISOCcyTableObj() {
		return( iSOCcyTableObj );
	}

	public ICFIntISOCtryTableObj getISOCtryTableObj() {
		return( iSOCtryTableObj );
	}

	public ICFIntISOCtryCcyTableObj getISOCtryCcyTableObj() {
		return( iSOCtryCcyTableObj );
	}

	public ICFIntISOCtryLangTableObj getISOCtryLangTableObj() {
		return( iSOCtryLangTableObj );
	}

	public ICFIntISOLangTableObj getISOLangTableObj() {
		return( iSOLangTableObj );
	}

	public ICFIntISOTZoneTableObj getISOTZoneTableObj() {
		return( iSOTZoneTableObj );
	}

	public ICFIntLicenseTableObj getLicenseTableObj() {
		return( licenseTableObj );
	}

	public ICFIntMajorVersionTableObj getMajorVersionTableObj() {
		return( majorVersionTableObj );
	}

	public ICFIntMimeTypeTableObj getMimeTypeTableObj() {
		return( mimeTypeTableObj );
	}

	public ICFIntMinorVersionTableObj getMinorVersionTableObj() {
		return( minorVersionTableObj );
	}

	public ICFIntSecDeviceTableObj getSecDeviceTableObj() {
		return( secDeviceTableObj );
	}

	public ICFIntSecGroupTableObj getSecGroupTableObj() {
		return( secGroupTableObj );
	}

	public ICFIntSecGrpIncTableObj getSecGrpIncTableObj() {
		return( secGrpIncTableObj );
	}

	public ICFIntSecGrpMembTableObj getSecGrpMembTableObj() {
		return( secGrpMembTableObj );
	}

	public ICFIntSecSessionTableObj getSecSessionTableObj() {
		return( secSessionTableObj );
	}

	public ICFIntSecUserTableObj getSecUserTableObj() {
		return( secUserTableObj );
	}

	public ICFIntServiceTableObj getServiceTableObj() {
		return( serviceTableObj );
	}

	public ICFIntServiceTypeTableObj getServiceTypeTableObj() {
		return( serviceTypeTableObj );
	}

	public ICFIntSubProjectTableObj getSubProjectTableObj() {
		return( subProjectTableObj );
	}

	public ICFIntSysClusterTableObj getSysClusterTableObj() {
		return( sysClusterTableObj );
	}

	public ICFIntTSecGroupTableObj getTSecGroupTableObj() {
		return( tSecGroupTableObj );
	}

	public ICFIntTSecGrpIncTableObj getTSecGrpIncTableObj() {
		return( tSecGrpIncTableObj );
	}

	public ICFIntTSecGrpMembTableObj getTSecGrpMembTableObj() {
		return( tSecGrpMembTableObj );
	}

	public ICFIntTenantTableObj getTenantTableObj() {
		return( tenantTableObj );
	}

	public ICFIntTldTableObj getTldTableObj() {
		return( tldTableObj );
	}

	public ICFIntTopDomainTableObj getTopDomainTableObj() {
		return( topDomainTableObj );
	}

	public ICFIntTopProjectTableObj getTopProjectTableObj() {
		return( topProjectTableObj );
	}

	public ICFIntURLProtocolTableObj getURLProtocolTableObj() {
		return( uRLProtocolTableObj );
	}
}
