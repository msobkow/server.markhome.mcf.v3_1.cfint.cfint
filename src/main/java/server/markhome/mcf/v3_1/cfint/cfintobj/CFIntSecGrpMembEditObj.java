// Description: Java 25 edit object instance implementation for CFInt SecGrpMemb.

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

public class CFIntSecGrpMembEditObj
	implements ICFIntSecGrpMembEditObj
{
	protected ICFSecSecGrpMembObj orig;
	protected ICFSecSecGrpMemb rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecClusterObj requiredOwnerCluster;
	protected ICFSecSecGroupObj requiredContainerGroup;
	protected ICFSecSecUserObj requiredParentUser;

	public CFIntSecGrpMembEditObj( ICFSecSecGrpMembObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecGrpMemb origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerCluster = null;
		requiredContainerGroup = null;
		requiredParentUser = null;
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecGrpMembTableObj().getClassCode() );
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
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecGrpMembObj retobj = getSchema().getSecGrpMembTableObj().realiseSecGrpMemb( (ICFIntSecGrpMembObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecGrpMemb().forget();
	}

	@Override
	public ICFSecSecGrpMembObj read() {
		ICFSecSecGrpMembObj retval = getOrigAsSecGrpMemb().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGrpMembObj read( boolean forceRead ) {
		ICFSecSecGrpMembObj retval = getOrigAsSecGrpMemb().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGrpMembObj create() {
		copyRecToOrig();
		ICFSecSecGrpMembObj retobj = ((ICFIntSchemaObj)getOrigAsSecGrpMemb().getSchema()).getSecGrpMembTableObj().createSecGrpMemb( getOrigAsSecGrpMemb() );
		if( retobj == getOrigAsSecGrpMemb() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecGrpMembEditObj update() {
		getSchema().getSecGrpMembTableObj().updateSecGrpMemb( (ICFSecSecGrpMembObj)this );
		return( null );
	}

	@Override
	public CFSecSecGrpMembEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecGrpMembTableObj().deleteSecGrpMemb( getOrigAsSecGrpMemb() );
		return( null );
	}

	@Override
	public ICFSecSecGrpMembTableObj getSecGrpMembTable() {
		return( orig.getSchema().getSecGrpMembTableObj() );
	}

	@Override
	public ICFSecSecGrpMembEditObj getEdit() {
		return( (ICFSecSecGrpMembEditObj)this );
	}

	@Override
	public ICFSecSecGrpMembEditObj getEditAsSecGrpMemb() {
		return( (ICFSecSecGrpMembEditObj)this );
	}

	@Override
	public ICFSecSecGrpMembEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecGrpMembObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecGrpMembObj getOrigAsSecGrpMemb() {
		return( (ICFSecSecGrpMembObj)orig );
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
	public ICFSecSecGrpMemb getRec() {
		if( rec == null ) {
			rec = getOrigAsSecGrpMemb().getSchema().getCFSecBackingStore().getFactorySecGrpMemb().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecGrpMemb value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerCluster = null;
			requiredContainerGroup = null;
			requiredParentUser = null;
		}
	}

	@Override
	public ICFSecSecGrpMemb getSecGrpMembRec() {
		return( (ICFSecSecGrpMemb)getRec() );
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
	public CFLibDbKeyHash256 getRequiredSecGrpMembId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSecGrpMembId(CFLibDbKeyHash256 secGrpMembId) {
		if (getPKey() != secGrpMembId) {
			setPKey(secGrpMembId);
		}
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
	public ICFSecClusterObj getRequiredOwnerCluster() {
		return( getRequiredOwnerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredOwnerCluster( boolean forceRead ) {
		if( forceRead || ( requiredOwnerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpMemb().getSchema()).getClusterTableObj().readClusterByIdIdx( getSecGrpMembRec().getRequiredClusterId() );
				requiredOwnerCluster = obj;
			}
		}
		return( requiredOwnerCluster );
	}

	@Override
	public void setRequiredOwnerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getSecGrpMembRec();
		}
		if( value != null ) {
			requiredOwnerCluster = value;
			getSecGrpMembRec().setRequiredOwnerCluster(value.getClusterRec());
		}
		requiredOwnerCluster = value;
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup() {
		return( getRequiredContainerGroup( false ) );
	}

	@Override
	public ICFSecSecGroupObj getRequiredContainerGroup( boolean forceRead ) {
		if( forceRead || ( requiredContainerGroup == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecSecGroupObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpMemb().getSchema()).getSecGroupTableObj().readSecGroupByIdIdx( getSecGrpMembRec().getRequiredSecGroupId() );
				requiredContainerGroup = obj;
				if( obj != null ) {
					requiredContainerGroup = obj;
				}
			}
		}
		return( requiredContainerGroup );
	}

	@Override
	public void setRequiredContainerGroup( ICFSecSecGroupObj value ) {
		if( rec == null ) {
			getSecGrpMembRec();
		}
		if( value != null ) {
			requiredContainerGroup = value;
			getSecGrpMembRec().setRequiredContainerGroup(value.getSecGroupRec());
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
				ICFSecSecUserObj obj = ((ICFIntSchemaObj)getOrigAsSecGrpMemb().getSchema()).getSecUserTableObj().readSecUserByIdIdx( getSecGrpMembRec().getRequiredSecUserId() );
				requiredParentUser = obj;
			}
		}
		return( requiredParentUser );
	}

	@Override
	public void setRequiredParentUser( ICFSecSecUserObj value ) {
		if( rec == null ) {
			getSecGrpMembRec();
		}
		if( value != null ) {
			requiredParentUser = value;
			getSecGrpMembRec().setRequiredParentUser(value.getSecUserRec());
		}
		else {
			requiredParentUser = null;
			getSecGrpMembRec().setRequiredParentUser((ICFSecSecUser)null);
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
		ICFSecSecGrpMemb origRec = getOrigAsSecGrpMemb().getSecGrpMembRec();
		ICFSecSecGrpMemb myRec = getSecGrpMembRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecGrpMemb origRec = getOrigAsSecGrpMemb().getSecGrpMembRec();
		ICFSecSecGrpMemb myRec = getSecGrpMembRec();
		myRec.set( origRec );
	}
}
