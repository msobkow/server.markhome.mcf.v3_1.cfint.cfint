// Description: Java 25 base object instance implementation for Service

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

public class CFIntServiceObj
	implements ICFIntServiceObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFSecServiceEditObj edit;
	protected ICFSecSchemaObj schema;
	protected CFLibDbKeyHash256 pKey;
	protected ICFSecService rec;
	protected ICFSecClusterObj requiredOwnerCluster;
	protected ICFSecHostNodeObj optionalContainerHost;
	protected ICFSecServiceTypeObj optionalParentServiceType;

	public CFIntServiceObj() {
		isNew = true;
		requiredOwnerCluster = null;
		optionalContainerHost = null;
		optionalParentServiceType = null;
	}

	public CFIntServiceObj( ICFSecSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredOwnerCluster = null;
		optionalContainerHost = null;
		optionalParentServiceType = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)schema).getServiceTableObj().getClassCode() );
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
		ICFSecServiceObj retobj = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().realiseService(
			(ICFSecServiceObj)this );
		return( (ICFSecServiceObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getServiceTableObj().reallyDeepDisposeService( (ICFSecServiceObj)this );
	}

	@Override
	public ICFSecServiceObj read() {
		ICFSecServiceObj retobj = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().readServiceByIdIdx( getPKey(), false );
		return( (ICFSecServiceObj)retobj );
	}

	@Override
	public ICFSecServiceObj read( boolean forceRead ) {
		ICFSecServiceObj retobj = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().readServiceByIdIdx( getPKey(), forceRead );
		return( (ICFSecServiceObj)retobj );
	}

	@Override
	public ICFSecServiceTableObj getServiceTable() {
		return( ((ICFIntSchemaObj)getSchema()).getServiceTableObj() );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
		schema = value;
	}

	@Override
	public ICFSecService getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFSecBackingStore().getFactoryService().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFSecBackingStore().getTableService().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecService value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFSecService ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFSecServiceRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredOwnerCluster = null;
		optionalContainerHost = null;
		optionalParentServiceType = null;
	}

	@Override
	public ICFSecService getServiceRec() {
		return( (ICFSecService)getRec() );
	}

	@Override
	public CFLibDbKeyHash256 getPKey() {
		return( pKey );
	}

	@Override
	public void setPKey( CFLibDbKeyHash256 value ) {
		if( pKey != value ) {
       		pKey = value;
			copyPKeyToRec();
		}
	}

	@Override
	public boolean getIsNew() {
		return( isNew );
	}

	@Override
	public void setIsNew( boolean value ) {
		isNew = value;
	}

	@Override
	public ICFSecServiceEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFSecServiceObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFSecServiceObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().lockService( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().newEditInstance( lockobj );
		return( (ICFSecServiceEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFSecServiceEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFSecServiceEditObj getEditAsService() {
		return( (ICFSecServiceEditObj)edit );
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
	public CFLibDbKeyHash256 getRequiredServiceId() {
		return( getPKey() );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster() {
		return( getRequiredOwnerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster( boolean forceRead ) {
		if( ( requiredOwnerCluster == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredOwnerCluster = ((ICFIntSchemaObj)getSchema()).getClusterTableObj().readClusterByIdIdx( getServiceRec().getRequiredClusterId(), forceRead );
			}
		}
		return( requiredOwnerCluster );
	}

	@Override
	public ICFSecHostNodeObj getOptionalContainerHost() {
		return( getOptionalContainerHost( false ) );
	}

	@Override
	public ICFSecHostNodeObj getOptionalContainerHost( boolean forceRead ) {
		if( ( optionalContainerHost == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				optionalContainerHost = ((ICFIntSchemaObj)getSchema()).getHostNodeTableObj().readHostNodeByIdIdx( getServiceRec().getRequiredHostNodeId(), forceRead );
			}
		}
		return( optionalContainerHost );
	}

	@Override
	public ICFSecServiceTypeObj getOptionalParentServiceType() {
		return( getOptionalParentServiceType( false ) );
	}

	@Override
	public ICFSecServiceTypeObj getOptionalParentServiceType( boolean forceRead ) {
		if( ( optionalParentServiceType == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				optionalParentServiceType = ((ICFIntSchemaObj)getSchema()).getServiceTypeTableObj().readServiceTypeByIdIdx( getServiceRec().getRequiredServiceTypeId(), forceRead );
			}
		}
		return( optionalParentServiceType );
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
	public void copyPKeyToRec() {
		if( rec != null ) {
			if (getPKey() != rec.getPKey()) {
				rec.setPKey(getPKey());
			}
		}
		if( edit != null ) {
			edit.copyPKeyToRec();
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
}
