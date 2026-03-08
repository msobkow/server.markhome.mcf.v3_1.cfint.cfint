// Description: Java 25 base object instance implementation for SecGrpMemb

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

public class CFIntSecGrpMembObj
	implements ICFIntSecGrpMembObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFSecSecGrpMembEditObj edit;
	protected ICFSecSchemaObj schema;
	protected CFLibDbKeyHash256 pKey;
	protected ICFSecSecGrpMemb rec;
	protected ICFSecClusterObj requiredOwnerCluster;
	protected ICFSecSecGroupObj requiredContainerGroup;
	protected ICFSecSecUserObj requiredParentUser;

	public CFIntSecGrpMembObj() {
		isNew = true;
		requiredOwnerCluster = null;
		requiredContainerGroup = null;
		requiredParentUser = null;
	}

	public CFIntSecGrpMembObj( ICFSecSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredOwnerCluster = null;
		requiredContainerGroup = null;
		requiredParentUser = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFSecSchemaObj)schema).getSecGrpMembTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecGrpMemb" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecSecGroupObj scope = getRequiredContainerGroup();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredSecGrpMembId();
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
	public ICFSecSecGrpMembObj realise() {
		ICFSecSecGrpMembObj retobj = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().realiseSecGrpMemb(
			(ICFSecSecGrpMembObj)this );
		return( (ICFSecSecGrpMembObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().reallyDeepDisposeSecGrpMemb( (ICFSecSecGrpMembObj)this );
	}

	@Override
	public ICFSecSecGrpMembObj read() {
		ICFSecSecGrpMembObj retobj = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByIdIdx( getPKey(), false );
		return( (ICFSecSecGrpMembObj)retobj );
	}

	@Override
	public ICFSecSecGrpMembObj read( boolean forceRead ) {
		ICFSecSecGrpMembObj retobj = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByIdIdx( getPKey(), forceRead );
		return( (ICFSecSecGrpMembObj)retobj );
	}

	@Override
	public ICFSecSecGrpMembTableObj getSecGrpMembTable() {
		return( ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj() );
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
	public ICFSecSecGrpMemb getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFSecBackingStore().getFactorySecGrpMemb().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFSecBackingStore().getTableSecGrpMemb().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecGrpMemb value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFSecSecGrpMemb ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFSecSecGrpMembRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredOwnerCluster = null;
		requiredContainerGroup = null;
		requiredParentUser = null;
	}

	@Override
	public ICFSecSecGrpMemb getSecGrpMembRec() {
		return( (ICFSecSecGrpMemb)getRec() );
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
	public ICFSecSecGrpMembEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFSecSecGrpMembObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFSecSecGrpMembObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().lockSecGrpMemb( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().newEditInstance( lockobj );
		return( (ICFSecSecGrpMembEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFSecSecGrpMembEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFSecSecGrpMembEditObj getEditAsSecGrpMemb() {
		return( (ICFSecSecGrpMembEditObj)edit );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecSecGrpMemb rec = getRec();
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
			ICFSecSecGrpMemb rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecGrpMembId() {
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
				requiredOwnerCluster = ((ICFIntSchemaObj)getSchema()).getClusterTableObj().readClusterByIdIdx( getSecGrpMembRec().getRequiredClusterId(), forceRead );
			}
		}
		return( requiredOwnerCluster );
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( ( requiredContainerGroup == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerGroup = ((ICFIntSchemaObj)getSchema()).getSecGroupTableObj().readSecGroupByIdIdx( getSecGrpMembRec().getRequiredSecGroupId(), forceRead );
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public ICFSecSecUserObj getRequiredParentUser() {
		return( getRequiredParentUser( false ) );
	}

	@Override
	public ICFSecSecUserObj getRequiredParentUser( boolean forceRead ) {
		if( ( requiredParentUser == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredParentUser = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( getSecGrpMembRec().getRequiredSecUserId(), forceRead );
			}
		}
		return( requiredParentUser );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getSecGrpMembRec().getRequiredClusterId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecGroupId() {
		return( getSecGrpMembRec().getRequiredSecGroupId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( getSecGrpMembRec().getRequiredSecUserId() );
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
