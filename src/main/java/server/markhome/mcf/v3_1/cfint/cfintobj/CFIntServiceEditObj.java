// Description: Java 25 edit object instance implementation for CFInt Service.

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
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFIntServiceEditObj
	implements ICFIntServiceEditObj
{
	protected ICFSecServiceObj orig;
	protected ICFSecService rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecClusterObj requiredOwnerCluster;
	protected ICFSecHostNodeObj optionalContainerHost;
	protected ICFSecServiceTypeObj optionalParentServiceType;

	public CFIntServiceEditObj( ICFSecServiceObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecService origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerCluster = null;
		optionalContainerHost = null;
		optionalParentServiceType = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecService rec = getRec();
			createdBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getCreatedByUserId() );
		}
		return( createdBy );
	}

	@Override
	public LocalDateTime getCreatedAt() {
		return( getRec().getCreatedAt() );
	}

	@Override
	public ICFSecSecUserObj getUpdatedBy() {
		if( updatedBy == null ) {
			ICFSecService rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public void setCreatedBy( ICFSecSecUserObj value ) {
		createdBy = value;
		if( value != null ) {
			getRec().setCreatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setCreatedAt( LocalDateTime value ) {
		getRec().setCreatedAt( value );
	}

	@Override
	public void setUpdatedBy( ICFSecSecUserObj value ) {
		updatedBy = value;
		if( value != null ) {
			getRec().setUpdatedByUserId( value.getRequiredSecUserId() );
		}
	}

	@Override
	public void setUpdatedAt( LocalDateTime value ) {
		getRec().setUpdatedAt( value );
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)orig.getSchema()).getServiceTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "Service" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecHostNodeObj scope = getOptionalContainerHost();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredServiceId();
		if (val != null) {
			objName = val.toString();
		}
		else {
			objName = "";
		}
		return( objName );
	}

	@Override
	public ICFLibAnyObj getObjQualifier( Class qualifyingClass ) {
		ICFLibAnyObj container = this;
		if( qualifyingClass != null ) {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				else if( qualifyingClass.isInstance( container ) ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		else {
			while( container != null ) {
				if( container instanceof ICFIntClusterObj ) {
					break;
				}
				else if( container instanceof ICFIntTenantObj ) {
					break;
				}
				container = container.getObjScope();
			}
		}
		return( container );
	}

	@Override
	public ICFLibAnyObj getNamedObject( Class qualifyingClass, String objName ) {
		ICFLibAnyObj topContainer = getObjQualifier( qualifyingClass );
		if( topContainer == null ) {
			return( null );
		}
		ICFLibAnyObj namedObject = topContainer.getNamedObject( objName );
		return( namedObject );
	}

	@Override
	public ICFLibAnyObj getNamedObject( String objName ) {
		String nextName;
		String remainingName;
		ICFLibAnyObj subObj = null;
		ICFLibAnyObj retObj;
		int nextDot = objName.indexOf( '.' );
		if( nextDot >= 0 ) {
			nextName = objName.substring( 0, nextDot );
			remainingName = objName.substring( nextDot + 1 );
		}
		else {
			nextName = objName;
			remainingName = null;
		}
		if( remainingName == null ) {
			retObj = subObj;
		}
		else if( subObj == null ) {
			retObj = null;
		}
		else {
			retObj = subObj.getNamedObject( remainingName );
		}
		return( retObj );
	}

	@Override
	public String getObjQualifiedName() {
		String qualName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				qualName = containerName + "." + qualName;
				container = container.getObjScope();
			}
		}
		return( qualName );
	}

	@Override
	public String getObjFullName() {
		String fullName = getObjName();
		ICFLibAnyObj container = getObjScope();
		String containerName;
		while( container != null ) {
			if( container instanceof ICFSecClusterObj ) {
				container = null;
			}
			else if( container instanceof ICFSecTenantObj ) {
				container = null;
			}
			else {
				containerName = container.getObjName();
				fullName = containerName + "." + fullName;
				container = container.getObjScope();
			}
		}
		return( fullName );
	}

	@Override
	public ICFSecServiceObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecServiceObj retobj = getSchema().getServiceTableObj().realiseService( (ICFIntServiceObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsService().forget();
	}

	@Override
	public ICFSecServiceObj read() {
		ICFSecServiceObj retval = getOrigAsService().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecServiceObj read( boolean forceRead ) {
		ICFSecServiceObj retval = getOrigAsService().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecServiceObj create() {
		copyRecToOrig();
		ICFSecServiceObj retobj = ((ICFIntSchemaObj)getOrigAsService().getSchema()).getServiceTableObj().createService( getOrigAsService() );
		if( retobj == getOrigAsService() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecServiceEditObj update() {
		getSchema().getServiceTableObj().updateService( (ICFSecServiceObj)this );
		return( null );
	}

	@Override
	public CFSecServiceEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getServiceTableObj().deleteService( getOrigAsService() );
		return( null );
	}

	@Override
	public ICFSecServiceTableObj getServiceTable() {
		return( orig.getSchema().getServiceTableObj() );
	}

	@Override
	public ICFSecServiceEditObj getEdit() {
		return( (ICFSecServiceEditObj)this );
	}

	@Override
	public ICFSecServiceEditObj getEditAsService() {
		return( (ICFSecServiceEditObj)this );
	}

	@Override
	public ICFSecServiceEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecServiceObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecServiceObj getOrigAsService() {
		return( (ICFSecServiceObj)orig );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFSecService getRec() {
		if( rec == null ) {
			rec = getOrigAsService().getSchema().getCFSecBackingStore().getFactoryService().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecService value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerCluster = null;
			optionalContainerHost = null;
			optionalParentServiceType = null;
		}
	}

	@Override
	public ICFSecService getServiceRec() {
		return( (ICFSecService)getRec() );
	}

	@Override
	public CFLibDbKeyHash256 getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( CFLibDbKeyHash256 value ) {
		orig.setPKey( value );
		copyPKeyToRec();
	}

	@Override
	public boolean getIsNew() {
		return( orig.getIsNew() );
	}

	@Override
	public void setIsNew( boolean value ) {
		orig.setIsNew( value );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredServiceId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredServiceId(CFLibDbKeyHash256 serviceId) {
		if (getPKey() != serviceId) {
			setPKey(serviceId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getServiceRec().getRequiredClusterId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredHostNodeId() {
		return( getServiceRec().getRequiredHostNodeId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredServiceTypeId() {
		return( getServiceRec().getRequiredServiceTypeId() );
	}

	@Override
	public short getRequiredHostPort() {
		return( getServiceRec().getRequiredHostPort() );
	}

	@Override
	public void setRequiredHostPort( short value ) {
		if( getServiceRec().getRequiredHostPort() != value ) {
			getServiceRec().setRequiredHostPort( value );
		}
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster() {
		return( getRequiredOwnerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster( boolean forceRead ) {
		if( forceRead || ( requiredOwnerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsService().getSchema()).getClusterTableObj().readClusterByIdIdx( getServiceRec().getRequiredClusterId() );
				requiredOwnerCluster = obj;
			}
		}
		return( requiredOwnerCluster );
	}

	@Override
	public void setRequiredOwnerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getServiceRec();
		}
		if( value != null ) {
			requiredOwnerCluster = value;
			getServiceRec().setRequiredOwnerCluster(value.getClusterRec());
		}
		requiredOwnerCluster = value;
	}

	@Override
	public ICFSecHostNodeObj getOptionalContainerHost() {
		return( getOptionalContainerHost( false ) );
	}

	@Override
	public ICFSecHostNodeObj getOptionalContainerHost( boolean forceRead ) {
		if( forceRead || ( optionalContainerHost == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecHostNodeObj obj = ((ICFIntSchemaObj)getOrigAsService().getSchema()).getHostNodeTableObj().readHostNodeByIdIdx( getServiceRec().getRequiredHostNodeId() );
				optionalContainerHost = obj;
				if( obj != null ) {
					optionalContainerHost = obj;
				}
			}
		}
		return( optionalContainerHost );
	}

	@Override
	public void setOptionalContainerHost( ICFSecHostNodeObj value ) {
		if( rec == null ) {
			getServiceRec();
		}
		if( value != null ) {
			optionalContainerHost = value;
			getServiceRec().setOptionalContainerHost(value.getHostNodeRec());
		}
		optionalContainerHost = value;
	}

	@Override
	public ICFSecServiceTypeObj getOptionalParentServiceType() {
		return( getOptionalParentServiceType( false ) );
	}

	@Override
	public ICFSecServiceTypeObj getOptionalParentServiceType( boolean forceRead ) {
		if( forceRead || ( optionalParentServiceType == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecServiceTypeObj obj = ((ICFIntSchemaObj)getOrigAsService().getSchema()).getServiceTypeTableObj().readServiceTypeByIdIdx( getServiceRec().getRequiredServiceTypeId() );
				optionalParentServiceType = obj;
			}
		}
		return( optionalParentServiceType );
	}

	@Override
	public void setOptionalParentServiceType( ICFSecServiceTypeObj value ) {
		if( rec == null ) {
			getServiceRec();
		}
		if( value != null ) {
			optionalParentServiceType = value;
			getServiceRec().setOptionalParentServiceType(value.getServiceTypeRec());
		}
		else {
			optionalParentServiceType = null;
			getServiceRec().setOptionalParentServiceType((ICFSecServiceType)null);
		}
		optionalParentServiceType = value;
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				rec.setPKey(getPKey());
			}
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				setPKey(rec.getPKey());
			}
		}
	}

	@Override
	public void copyRecToOrig() {
		ICFSecService origRec = getOrigAsService().getServiceRec();
		ICFSecService myRec = getServiceRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecService origRec = getOrigAsService().getServiceRec();
		ICFSecService myRec = getServiceRec();
		myRec.set( origRec );
	}
}
