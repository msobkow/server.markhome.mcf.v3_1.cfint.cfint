// Description: Java 25 edit object instance implementation for CFInt SecUser.

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

public class CFIntSecUserEditObj
	implements ICFIntSecUserEditObj
{
	protected ICFSecSecUserObj orig;
	protected ICFSecSecUser rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected List<ICFSecSecDeviceObj> optionalComponentsSecDev;
	protected ICFSecSecDeviceObj optionalLookupDefDev;
	protected List<ICFSecSecGrpMembObj> optionalChildrenSecGrpMemb;
	protected List<ICFSecTSecGrpMembObj> optionalChildrenTSecGrpMemb;

	public CFIntSecUserEditObj( ICFSecSecUserObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecUser origRec = orig.getRec();
		rec.set( origRec );
		optionalLookupDefDev = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecSecUser rec = getRec();
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
			ICFSecSecUser rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecUserTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecUser" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredLoginId();
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
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getSecDeviceTableObj().readSecDeviceByNameIdx( getRequiredSecUserId(),
				nextName, false );
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
	public ICFSecSecUserObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecUserObj retobj = getSchema().getSecUserTableObj().realiseSecUser( (ICFIntSecUserObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecUser().forget();
	}

	@Override
	public ICFSecSecUserObj read() {
		ICFSecSecUserObj retval = getOrigAsSecUser().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecUserObj read( boolean forceRead ) {
		ICFSecSecUserObj retval = getOrigAsSecUser().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecUserObj create() {
		copyRecToOrig();
		ICFSecSecUserObj retobj = ((ICFIntSchemaObj)getOrigAsSecUser().getSchema()).getSecUserTableObj().createSecUser( getOrigAsSecUser() );
		if( retobj == getOrigAsSecUser() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecUserEditObj update() {
		getSchema().getSecUserTableObj().updateSecUser( (ICFSecSecUserObj)this );
		return( null );
	}

	@Override
	public CFSecSecUserEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecUserTableObj().deleteSecUser( getOrigAsSecUser() );
		return( null );
	}

	@Override
	public ICFSecSecUserTableObj getSecUserTable() {
		return( orig.getSchema().getSecUserTableObj() );
	}

	@Override
	public ICFSecSecUserEditObj getEdit() {
		return( (ICFSecSecUserEditObj)this );
	}

	@Override
	public ICFSecSecUserEditObj getEditAsSecUser() {
		return( (ICFSecSecUserEditObj)this );
	}

	@Override
	public ICFSecSecUserEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecUserObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecUserObj getOrigAsSecUser() {
		return( (ICFSecSecUserObj)orig );
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
	public ICFSecSecUser getRec() {
		if( rec == null ) {
			rec = getOrigAsSecUser().getSchema().getCFSecBackingStore().getFactorySecUser().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecUser value ) {
		if( rec != value ) {
			rec = value;
			optionalLookupDefDev = null;
		}
	}

	@Override
	public ICFSecSecUser getSecUserRec() {
		return( (ICFSecSecUser)getRec() );
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
	public CFLibDbKeyHash256 getRequiredSecUserId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSecUserId(CFLibDbKeyHash256 secUserId) {
		if (getPKey() != secUserId) {
			setPKey(secUserId);
			optionalComponentsSecDev = null;
			optionalLookupDefDev = null;
			optionalChildrenSecGrpMemb = null;
			optionalChildrenTSecGrpMemb = null;
		}
	}

	@Override
	public String getRequiredLoginId() {
		return( getSecUserRec().getRequiredLoginId() );
	}

	@Override
	public void setRequiredLoginId( String value ) {
		if( getSecUserRec().getRequiredLoginId() != value ) {
			getSecUserRec().setRequiredLoginId( value );
		}
	}

	@Override
	public String getRequiredEMailAddress() {
		return( getSecUserRec().getRequiredEMailAddress() );
	}

	@Override
	public void setRequiredEMailAddress( String value ) {
		if( getSecUserRec().getRequiredEMailAddress() != value ) {
			getSecUserRec().setRequiredEMailAddress( value );
		}
	}

	@Override
	public CFLibUuid6 getOptionalEMailConfirmUuid6() {
		return( getSecUserRec().getOptionalEMailConfirmUuid6() );
	}

	@Override
	public void setOptionalEMailConfirmUuid6( CFLibUuid6 value ) {
		if( getSecUserRec().getOptionalEMailConfirmUuid6() != value ) {
			getSecUserRec().setOptionalEMailConfirmUuid6( value );
		}
	}

	@Override
	public CFLibDbKeyHash256 getOptionalDfltDevUserId() {
		return( getSecUserRec().getOptionalDfltDevUserId() );
	}

	@Override
	public String getOptionalDfltDevName() {
		return( getSecUserRec().getOptionalDfltDevName() );
	}

	@Override
	public String getRequiredPasswordHash() {
		return( getSecUserRec().getRequiredPasswordHash() );
	}

	@Override
	public void setRequiredPasswordHash( String value ) {
		if( getSecUserRec().getRequiredPasswordHash() != value ) {
			getSecUserRec().setRequiredPasswordHash( value );
		}
	}

	@Override
	public CFLibUuid6 getOptionalPasswordResetUuid6() {
		return( getSecUserRec().getOptionalPasswordResetUuid6() );
	}

	@Override
	public void setOptionalPasswordResetUuid6( CFLibUuid6 value ) {
		if( getSecUserRec().getOptionalPasswordResetUuid6() != value ) {
			getSecUserRec().setOptionalPasswordResetUuid6( value );
		}
	}

	@Override
	public List<ICFSecSecDeviceObj> getOptionalComponentsSecDev() {
		List<ICFSecSecDeviceObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecDeviceTableObj().readSecDeviceByUserIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecSecDeviceObj> getOptionalComponentsSecDev( boolean forceRead ) {
		List<ICFSecSecDeviceObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecDeviceTableObj().readSecDeviceByUserIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public ICFSecSecDeviceObj getOptionalLookupDefDev() {
		return( getOptionalLookupDefDev( false ) );
	}

	@Override
	public ICFSecSecDeviceObj getOptionalLookupDefDev( boolean forceRead ) {
		if( forceRead || ( optionalLookupDefDev == null ) ) {
			boolean anyMissing = false;
			if( getSecUserRec().getOptionalDfltDevUserId() == null ) {
				anyMissing = true;
			}
			if( getSecUserRec().getOptionalDfltDevName() == null ) {
				anyMissing = true;
			}
			if( ! anyMissing ) {
				ICFSecSecDeviceObj obj = ((ICFIntSchemaObj)getOrigAsSecUser().getSchema()).getSecDeviceTableObj().readSecDeviceByIdIdx( getSecUserRec().getOptionalDfltDevUserId(),
					getSecUserRec().getOptionalDfltDevName() );
				optionalLookupDefDev = obj;
			}
		}
		return( optionalLookupDefDev );
	}

	@Override
	public void setOptionalLookupDefDev( ICFSecSecDeviceObj value ) {
		if( rec == null ) {
			getSecUserRec();
		}
		if( value != null ) {
			optionalLookupDefDev = value;
			getSecUserRec().setOptionalLookupDefDev(value.getSecDeviceRec());
		}
		else {
			optionalLookupDefDev = null;
			getSecUserRec().setOptionalLookupDefDev((ICFSecSecDevice)null);
		}
		optionalLookupDefDev = value;
	}

	@Override
	public List<ICFSecSecGrpMembObj> getOptionalChildrenSecGrpMemb() {
		List<ICFSecSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByUserIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpMembObj> getOptionalChildrenSecGrpMemb( boolean forceRead ) {
		List<ICFSecSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByUserIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> getOptionalChildrenTSecGrpMemb() {
		List<ICFSecTSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpMembTableObj().readTSecGrpMembByUserIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecTSecGrpMembObj> getOptionalChildrenTSecGrpMemb( boolean forceRead ) {
		List<ICFSecTSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTSecGrpMembTableObj().readTSecGrpMembByUserIdx( getPKey(),
			forceRead );
		return( retval );
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
		ICFSecSecUser origRec = getOrigAsSecUser().getSecUserRec();
		ICFSecSecUser myRec = getSecUserRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecUser origRec = getOrigAsSecUser().getSecUserRec();
		ICFSecSecUser myRec = getSecUserRec();
		myRec.set( origRec );
	}
}
