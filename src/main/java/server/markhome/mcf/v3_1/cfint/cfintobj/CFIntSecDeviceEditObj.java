// Description: Java 25 edit object instance implementation for CFInt SecDevice.

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

public class CFIntSecDeviceEditObj
	implements ICFIntSecDeviceEditObj
{
	protected ICFSecSecDeviceObj orig;
	protected ICFSecSecDevice rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecSecUserObj requiredContainerSecUser;

	public CFIntSecDeviceEditObj( ICFSecSecDeviceObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecDevice origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerSecUser = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecSecDevice rec = getRec();
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
			ICFSecSecDevice rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecDeviceTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecDevice" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecSecUserObj scope = getRequiredContainerSecUser();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredDevName();
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
	public ICFSecSecDeviceObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecDeviceObj retobj = getSchema().getSecDeviceTableObj().realiseSecDevice( (ICFIntSecDeviceObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecDevice().forget();
	}

	@Override
	public ICFSecSecDeviceObj read() {
		ICFSecSecDeviceObj retval = getOrigAsSecDevice().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecDeviceObj read( boolean forceRead ) {
		ICFSecSecDeviceObj retval = getOrigAsSecDevice().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecDeviceObj create() {
		copyRecToOrig();
		ICFSecSecDeviceObj retobj = ((ICFIntSchemaObj)getOrigAsSecDevice().getSchema()).getSecDeviceTableObj().createSecDevice( getOrigAsSecDevice() );
		if( retobj == getOrigAsSecDevice() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecDeviceEditObj update() {
		getSchema().getSecDeviceTableObj().updateSecDevice( (ICFSecSecDeviceObj)this );
		return( null );
	}

	@Override
	public CFSecSecDeviceEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecDeviceTableObj().deleteSecDevice( getOrigAsSecDevice() );
		return( null );
	}

	@Override
	public ICFSecSecDeviceTableObj getSecDeviceTable() {
		return( orig.getSchema().getSecDeviceTableObj() );
	}

	@Override
	public ICFSecSecDeviceEditObj getEdit() {
		return( (ICFSecSecDeviceEditObj)this );
	}

	@Override
	public ICFSecSecDeviceEditObj getEditAsSecDevice() {
		return( (ICFSecSecDeviceEditObj)this );
	}

	@Override
	public ICFSecSecDeviceEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecDeviceObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecDeviceObj getOrigAsSecDevice() {
		return( (ICFSecSecDeviceObj)orig );
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
	public ICFSecSecDevice getRec() {
		if( rec == null ) {
			rec = getOrigAsSecDevice().getSchema().getCFSecBackingStore().getFactorySecDevice().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecDevice value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerSecUser = null;
		}
	}

	@Override
	public ICFSecSecDevice getSecDeviceRec() {
		return( (ICFSecSecDevice)getRec() );
	}

	@Override
	public ICFSecSecDevicePKey getPKey() {
		return( orig.getPKey() );
	}

	@Override
	public void setPKey( ICFSecSecDevicePKey value ) {
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
		return( getPKey().getRequiredSecUserId() );
	}

	@Override
	public String getRequiredDevName() {
		return( getPKey().getRequiredDevName() );
	}

	@Override
	public void setRequiredDevName(String devName) {
		if ((getPKey().getRequiredDevName() != devName ) || ( getSecDeviceRec().getRequiredDevName() != devName )) {
			getPKey().setRequiredDevName(devName);
			getSecDeviceRec().setRequiredDevName( devName );
		}
	}

	@Override
	public ICFSecSecUserObj getRequiredContainerSecUser() {
		return( getRequiredContainerSecUser( false ) );
	}

	@Override
	public ICFSecSecUserObj getRequiredContainerSecUser( boolean forceRead ) {
		if( forceRead || ( requiredContainerSecUser == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecSecUserObj obj = ((ICFIntSchemaObj)getOrigAsSecDevice().getSchema()).getSecUserTableObj().readSecUserByIdIdx( getPKey().getRequiredSecUserId() );
				requiredContainerSecUser = obj;
				if( obj != null ) {
					requiredContainerSecUser = obj;
				}
			}
		}
		return( requiredContainerSecUser );
	}

	@Override
	public void setRequiredContainerSecUser( ICFSecSecUserObj value ) {
		if( rec == null ) {
			getSecDeviceRec();
		}
		if( value != null ) {
			requiredContainerSecUser = value;
			getSecDeviceRec().setRequiredContainerSecUser(value.getSecUserRec());
		}
		requiredContainerSecUser = value;
	}

	@Override
	public void copyPKeyToRec() {
		if( rec != null ) {
			rec.getPKey().setRequiredContainerSecUser(getPKey().getRequiredContainerSecUser());
			rec.getPKey().setRequiredDevName(getPKey().getRequiredDevName());
		}
	}

	@Override
	public void copyRecToPKey() {
		if( rec != null ) {
			getPKey().setRequiredContainerSecUser(rec.getPKey().getRequiredContainerSecUser());
			getPKey().setRequiredDevName(rec.getPKey().getRequiredDevName());
		}
	}

	@Override
	public void copyRecToOrig() {
		ICFSecSecDevice origRec = getOrigAsSecDevice().getSecDeviceRec();
		ICFSecSecDevice myRec = getSecDeviceRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecDevice origRec = getOrigAsSecDevice().getSecDeviceRec();
		ICFSecSecDevice myRec = getSecDeviceRec();
		myRec.set( origRec );
	}
}
