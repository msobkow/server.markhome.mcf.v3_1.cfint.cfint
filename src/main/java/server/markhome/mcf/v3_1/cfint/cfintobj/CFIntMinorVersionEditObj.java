// Description: Java 25 edit object instance implementation for CFInt MinorVersion.

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

public class CFIntMinorVersionEditObj
	implements ICFIntMinorVersionEditObj
{
	protected ICFIntMinorVersionObj orig;
	protected ICFIntMinorVersion rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFIntMajorVersionObj requiredContainerParentMajVer;

	public CFIntMinorVersionEditObj( ICFIntMinorVersionObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFIntMinorVersion origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerTenant = null;
		requiredContainerParentMajVer = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFIntMinorVersion rec = getRec();
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
			ICFIntMinorVersion rec = getRec();
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
		return( ((ICFIntSchemaObj)orig.getSchema()).getMinorVersionTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "MinorVersion" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFIntMajorVersionObj scope = getRequiredContainerParentMajVer();
		return( scope );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredName();
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
			else if( container instanceof ICFIntTenantObj ) {
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
	public ICFIntMinorVersionObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFIntMinorVersionObj retobj = getSchema().getMinorVersionTableObj().realiseMinorVersion( (ICFIntMinorVersionObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsMinorVersion().forget();
	}

	@Override
	public ICFIntMinorVersionObj read() {
		ICFIntMinorVersionObj retval = getOrigAsMinorVersion().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntMinorVersionObj read( boolean forceRead ) {
		ICFIntMinorVersionObj retval = getOrigAsMinorVersion().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntMinorVersionObj create() {
		copyRecToOrig();
		ICFIntMinorVersionObj retobj = ((ICFIntSchemaObj)getOrigAsMinorVersion().getSchema()).getMinorVersionTableObj().createMinorVersion( getOrigAsMinorVersion() );
		if( retobj == getOrigAsMinorVersion() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFIntMinorVersionEditObj update() {
		getSchema().getMinorVersionTableObj().updateMinorVersion( (ICFIntMinorVersionObj)this );
		return( null );
	}

	@Override
	public CFIntMinorVersionEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getMinorVersionTableObj().deleteMinorVersion( getOrigAsMinorVersion() );
		return( null );
	}

	@Override
	public ICFIntMinorVersionTableObj getMinorVersionTable() {
		return( orig.getSchema().getMinorVersionTableObj() );
	}

	@Override
	public ICFIntMinorVersionEditObj getEdit() {
		return( (ICFIntMinorVersionEditObj)this );
	}

	@Override
	public ICFIntMinorVersionEditObj getEditAsMinorVersion() {
		return( (ICFIntMinorVersionEditObj)this );
	}

	@Override
	public ICFIntMinorVersionEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFIntMinorVersionObj getOrig() {
		return( orig );
	}

	@Override
	public ICFIntMinorVersionObj getOrigAsMinorVersion() {
		return( (ICFIntMinorVersionObj)orig );
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( orig.getSchema() );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
		orig.setSchema(value);
	}

	@Override
	public ICFIntMinorVersion getRec() {
		if( rec == null ) {
			rec = getOrigAsMinorVersion().getSchema().getCFIntBackingStore().getFactoryMinorVersion().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntMinorVersion value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerTenant = null;
			requiredContainerParentMajVer = null;
		}
	}

	@Override
	public ICFIntMinorVersion getMinorVersionRec() {
		return( (ICFIntMinorVersion)getRec() );
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
	public CFLibDbKeyHash256 getRequiredId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredId(CFLibDbKeyHash256 id) {
		if (getPKey() != id) {
			setPKey(id);
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getMinorVersionRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredMajorVersionId() {
		return( getMinorVersionRec().getRequiredMajorVersionId() );
	}

	@Override
	public String getRequiredName() {
		return( getMinorVersionRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getMinorVersionRec().getRequiredName() != value ) {
			getMinorVersionRec().setRequiredName( value );
		}
	}

	@Override
	public String getOptionalDescription() {
		return( getMinorVersionRec().getOptionalDescription() );
	}

	@Override
	public void setOptionalDescription( String value ) {
		if( getMinorVersionRec().getOptionalDescription() != value ) {
			getMinorVersionRec().setOptionalDescription( value );
		}
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
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsMinorVersion().getSchema()).getTenantTableObj().readTenantByIdIdx( getMinorVersionRec().getRequiredTenantId() );
				requiredOwnerTenant = obj;
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public void setRequiredOwnerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getMinorVersionRec();
		}
		if( value != null ) {
			requiredOwnerTenant = value;
			getMinorVersionRec().setRequiredOwnerTenant(value.getTenantRec());
		}
		requiredOwnerTenant = value;
	}

	@Override
	public ICFIntMajorVersionObj getRequiredContainerParentMajVer() {
		return( getRequiredContainerParentMajVer( false ) );
	}

	@Override
	public ICFIntMajorVersionObj getRequiredContainerParentMajVer( boolean forceRead ) {
		if( forceRead || ( requiredContainerParentMajVer == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFIntMajorVersionObj obj = ((ICFIntSchemaObj)getOrigAsMinorVersion().getSchema()).getMajorVersionTableObj().readMajorVersionByIdIdx( getMinorVersionRec().getRequiredMajorVersionId() );
				requiredContainerParentMajVer = obj;
				if( obj != null ) {
					requiredContainerParentMajVer = obj;
				}
			}
		}
		return( requiredContainerParentMajVer );
	}

	@Override
	public void setRequiredContainerParentMajVer( ICFIntMajorVersionObj value ) {
		if( rec == null ) {
			getMinorVersionRec();
		}
		if( value != null ) {
			requiredContainerParentMajVer = value;
			getMinorVersionRec().setRequiredContainerParentMajVer(value.getMajorVersionRec());
		}
		requiredContainerParentMajVer = value;
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
		ICFIntMinorVersion origRec = getOrigAsMinorVersion().getMinorVersionRec();
		ICFIntMinorVersion myRec = getMinorVersionRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFIntMinorVersion origRec = getOrigAsMinorVersion().getMinorVersionRec();
		ICFIntMinorVersion myRec = getMinorVersionRec();
		myRec.set( origRec );
	}
}
