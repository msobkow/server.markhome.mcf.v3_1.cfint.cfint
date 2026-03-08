// Description: Java 25 edit object instance implementation for CFInt TSecGrpMemb.

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

public class CFIntTSecGrpMembEditObj
	implements ICFIntTSecGrpMembEditObj
{
	protected ICFSecTSecGrpMembObj orig;
	protected ICFSecTSecGrpMemb rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFSecTSecGroupObj requiredContainerGroup;
	protected ICFSecSecUserObj requiredParentUser;

	public CFIntTSecGrpMembEditObj( ICFSecTSecGrpMembObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecTSecGrpMemb origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerTenant = null;
		requiredContainerGroup = null;
		requiredParentUser = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecTSecGrpMemb rec = getRec();
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
			ICFSecTSecGrpMemb rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getTSecGrpMembTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TSecGrpMemb" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecTSecGroupObj scope = getRequiredContainerGroup();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		CFLibDbKeyHash256 val = rec.getRequiredTSecGrpMembId();
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
	public ICFSecTSecGrpMembObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecTSecGrpMembObj retobj = getSchema().getTSecGrpMembTableObj().realiseTSecGrpMemb( (ICFIntTSecGrpMembObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTSecGrpMemb().forget();
	}

	@Override
	public ICFSecTSecGrpMembObj read() {
		ICFSecTSecGrpMembObj retval = getOrigAsTSecGrpMemb().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGrpMembObj read( boolean forceRead ) {
		ICFSecTSecGrpMembObj retval = getOrigAsTSecGrpMemb().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecTSecGrpMembObj create() {
		copyRecToOrig();
		ICFSecTSecGrpMembObj retobj = ((ICFIntSchemaObj)getOrigAsTSecGrpMemb().getSchema()).getTSecGrpMembTableObj().createTSecGrpMemb( getOrigAsTSecGrpMemb() );
		if( retobj == getOrigAsTSecGrpMemb() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecTSecGrpMembEditObj update() {
		getSchema().getTSecGrpMembTableObj().updateTSecGrpMemb( (ICFSecTSecGrpMembObj)this );
		return( null );
	}

	@Override
	public CFSecTSecGrpMembEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTSecGrpMembTableObj().deleteTSecGrpMemb( getOrigAsTSecGrpMemb() );
		return( null );
	}

	@Override
	public ICFSecTSecGrpMembTableObj getTSecGrpMembTable() {
		return( orig.getSchema().getTSecGrpMembTableObj() );
	}

	@Override
	public ICFSecTSecGrpMembEditObj getEdit() {
		return( (ICFSecTSecGrpMembEditObj)this );
	}

	@Override
	public ICFSecTSecGrpMembEditObj getEditAsTSecGrpMemb() {
		return( (ICFSecTSecGrpMembEditObj)this );
	}

	@Override
	public ICFSecTSecGrpMembEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecTSecGrpMembObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecTSecGrpMembObj getOrigAsTSecGrpMemb() {
		return( (ICFSecTSecGrpMembObj)orig );
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
	public ICFSecTSecGrpMemb getRec() {
		if( rec == null ) {
			rec = getOrigAsTSecGrpMemb().getSchema().getCFSecBackingStore().getFactoryTSecGrpMemb().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecTSecGrpMemb value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerTenant = null;
			requiredContainerGroup = null;
			requiredParentUser = null;
		}
	}

	@Override
	public ICFSecTSecGrpMemb getTSecGrpMembRec() {
		return( (ICFSecTSecGrpMemb)getRec() );
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
	public CFLibDbKeyHash256 getRequiredTSecGrpMembId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredTSecGrpMembId(CFLibDbKeyHash256 tSecGrpMembId) {
		if (getPKey() != tSecGrpMembId) {
			setPKey(tSecGrpMembId);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTSecGrpMembRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTSecGroupId() {
		return( getTSecGrpMembRec().getRequiredTSecGroupId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( getTSecGrpMembRec().getRequiredSecUserId() );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant() {
		return( getRequiredOwnerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredOwnerTenant( boolean forceRead ) {
		if( forceRead || ( requiredOwnerTenant == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpMemb().getSchema()).getTenantTableObj().readTenantByIdIdx( getTSecGrpMembRec().getRequiredTenantId() );
				requiredOwnerTenant = obj;
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public void setRequiredOwnerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getTSecGrpMembRec();
		}
		if( value != null ) {
			requiredOwnerTenant = value;
			getTSecGrpMembRec().setRequiredOwnerTenant(value.getTenantRec());
		}
		requiredOwnerTenant = value;
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecTSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( forceRead || ( requiredContainerGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpMemb().getSchema()).getTSecGroupTableObj().readTSecGroupByIdIdx( getTSecGrpMembRec().getRequiredTSecGroupId() );
				requiredContainerGroup = obj;
				if( obj != null ) {
					requiredContainerGroup = obj;
				}
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public void setRequiredContainerGroup( ICFSecTSecGroupObj value ) {
		if( rec == null ) {
			getTSecGrpMembRec();
		}
		if( value != null ) {
			requiredContainerGroup = value;
			getTSecGrpMembRec().setRequiredContainerGroup(value.getTSecGroupRec());
		}
		requiredContainerGroup = value;
	}

	@Override
	public ICFSecSecUserObj getRequiredParentUser() {
		return( getRequiredParentUser( false ) );
	}

	@Override
	public ICFSecSecUserObj getRequiredParentUser( boolean forceRead ) {
		if( forceRead || ( requiredParentUser == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecSecUserObj obj = ((ICFIntSchemaObj)getOrigAsTSecGrpMemb().getSchema()).getSecUserTableObj().readSecUserByIdIdx( getTSecGrpMembRec().getRequiredSecUserId() );
				requiredParentUser = obj;
			}
		}
		return( requiredParentUser );
	}

	@Override
	public void setRequiredParentUser( ICFSecSecUserObj value ) {
		if( rec == null ) {
			getTSecGrpMembRec();
		}
		if( value != null ) {
			requiredParentUser = value;
			getTSecGrpMembRec().setRequiredParentUser(value.getSecUserRec());
		}
		else {
			requiredParentUser = null;
			getTSecGrpMembRec().setRequiredParentUser((ICFSecSecUser)null);
		}
		requiredParentUser = value;
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
		ICFSecTSecGrpMemb origRec = getOrigAsTSecGrpMemb().getTSecGrpMembRec();
		ICFSecTSecGrpMemb myRec = getTSecGrpMembRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecTSecGrpMemb origRec = getOrigAsTSecGrpMemb().getTSecGrpMembRec();
		ICFSecTSecGrpMemb myRec = getTSecGrpMembRec();
		myRec.set( origRec );
	}
}
