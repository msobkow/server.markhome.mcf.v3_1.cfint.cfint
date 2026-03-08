// Description: Java 25 base object instance implementation for TSecGrpInc

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

public class CFIntTSecGrpIncObj
	implements ICFIntTSecGrpIncObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFSecTSecGrpIncEditObj edit;
	protected ICFSecSchemaObj schema;
	protected CFLibDbKeyHash256 pKey;
	protected ICFSecTSecGrpInc rec;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFSecTSecGroupObj requiredContainerGroup;
	protected ICFSecTSecGroupObj requiredParentSubGroup;

	public CFIntTSecGrpIncObj() {
		isNew = true;
		requiredOwnerTenant = null;
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	public CFIntTSecGrpIncObj( ICFSecSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredOwnerTenant = null;
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)schema).getTSecGrpIncTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TSecGrpInc" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecTSecGroupObj scope = getRequiredContainerGroup();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredTSecGrpIncId();
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
	public ICFSecTSecGrpIncObj realise() {
		ICFSecTSecGrpIncObj retobj = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().realiseTSecGrpInc(
			(ICFSecTSecGrpIncObj)this );
		return( (ICFSecTSecGrpIncObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().reallyDeepDisposeTSecGrpInc( (ICFSecTSecGrpIncObj)this );
	}

	@Override
	public ICFSecTSecGrpIncObj read() {
		ICFSecTSecGrpIncObj retobj = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByIdIdx( getPKey(), false );
		return( (ICFSecTSecGrpIncObj)retobj );
	}

	@Override
	public ICFSecTSecGrpIncObj read( boolean forceRead ) {
		ICFSecTSecGrpIncObj retobj = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().readTSecGrpIncByIdIdx( getPKey(), forceRead );
		return( (ICFSecTSecGrpIncObj)retobj );
	}

	@Override
	public ICFSecTSecGrpIncTableObj getTSecGrpIncTable() {
		return( ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj() );
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
	public ICFSecTSecGrpInc getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFSecBackingStore().getFactoryTSecGrpInc().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFSecBackingStore().getTableTSecGrpInc().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecTSecGrpInc value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFSecTSecGrpInc ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFSecTSecGrpIncRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredOwnerTenant = null;
		requiredContainerGroup = null;
		requiredParentSubGroup = null;
	}

	@Override
	public ICFSecTSecGrpInc getTSecGrpIncRec() {
		return( (ICFSecTSecGrpInc)getRec() );
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
	public ICFSecTSecGrpIncEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFSecTSecGrpIncObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFSecTSecGrpIncObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().lockTSecGrpInc( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getTSecGrpIncTableObj().newEditInstance( lockobj );
		return( (ICFSecTSecGrpIncEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFSecTSecGrpIncEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFSecTSecGrpIncEditObj getEditAsTSecGrpInc() {
		return( (ICFSecTSecGrpIncEditObj)edit );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecTSecGrpInc rec = getRec();
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
			ICFSecTSecGrpInc rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGrpIncId() {
		return( getPKey() );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant() {
		return( getRequiredOwnerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant( boolean forceRead ) {
		if( ( requiredOwnerTenant == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredOwnerTenant = ((ICFIntSchemaObj)getSchema()).getTenantTableObj().readTenantByIdIdx( getTSecGrpIncRec().getRequiredTenantId(), forceRead );
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( ( requiredContainerGroup == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerGroup = ((ICFIntSchemaObj)getSchema()).getTSecGroupTableObj().readTSecGroupByIdIdx( getTSecGrpIncRec().getRequiredTSecGroupId(), forceRead );
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredParentSubGroup() {
		return( getRequiredParentSubGroup( false ) );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredParentSubGroup( boolean forceRead ) {
		if( ( requiredParentSubGroup == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredParentSubGroup = ((ICFIntSchemaObj)getSchema()).getTSecGroupTableObj().readTSecGroupByIdIdx( getTSecGrpIncRec().getRequiredIncludeGroupId(), forceRead );
			}
		}
		return( requiredParentSubGroup );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTSecGrpIncRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		return( getTSecGrpIncRec().getRequiredTSecGroupId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredIncludeGroupId() {
		return( getTSecGrpIncRec().getRequiredIncludeGroupId() );
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
