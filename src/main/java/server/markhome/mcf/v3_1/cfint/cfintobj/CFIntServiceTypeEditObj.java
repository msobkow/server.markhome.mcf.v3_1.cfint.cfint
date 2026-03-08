// Description: Java 25 edit object instance implementation for CFInt ServiceType.

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

public class CFIntServiceTypeEditObj
	implements ICFIntServiceTypeEditObj
{
	protected ICFSecServiceTypeObj orig;
	protected ICFSecServiceType rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected List<ICFSecServiceObj> optionalChildrenDeployed;

	public CFIntServiceTypeEditObj( ICFSecServiceTypeObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecServiceType origRec = orig.getRec();
		rec.set( origRec );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecServiceType rec = getRec();
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
			ICFSecServiceType rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getServiceTypeTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "ServiceType" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( null );
	}

	@Override
	public String getObjName() {
		String objName;
		objName = getRequiredDescription();
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
	public ICFSecServiceTypeObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecServiceTypeObj retobj = getSchema().getServiceTypeTableObj().realiseServiceType( (ICFIntServiceTypeObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsServiceType().forget();
	}

	@Override
	public ICFSecServiceTypeObj read() {
		ICFSecServiceTypeObj retval = getOrigAsServiceType().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecServiceTypeObj read( boolean forceRead ) {
		ICFSecServiceTypeObj retval = getOrigAsServiceType().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecServiceTypeObj create() {
		copyRecToOrig();
		ICFSecServiceTypeObj retobj = ((ICFIntSchemaObj)getOrigAsServiceType().getSchema()).getServiceTypeTableObj().createServiceType( getOrigAsServiceType() );
		if( retobj == getOrigAsServiceType() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecServiceTypeEditObj update() {
		getSchema().getServiceTypeTableObj().updateServiceType( (ICFSecServiceTypeObj)this );
		return( null );
	}

	@Override
	public CFSecServiceTypeEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getServiceTypeTableObj().deleteServiceType( getOrigAsServiceType() );
		return( null );
	}

	@Override
	public ICFSecServiceTypeTableObj getServiceTypeTable() {
		return( orig.getSchema().getServiceTypeTableObj() );
	}

	@Override
	public ICFSecServiceTypeEditObj getEdit() {
		return( (ICFSecServiceTypeEditObj)this );
	}

	@Override
	public ICFSecServiceTypeEditObj getEditAsServiceType() {
		return( (ICFSecServiceTypeEditObj)this );
	}

	@Override
	public ICFSecServiceTypeEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecServiceTypeObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecServiceTypeObj getOrigAsServiceType() {
		return( (ICFSecServiceTypeObj)orig );
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
	public ICFSecServiceType getRec() {
		if( rec == null ) {
			rec = getOrigAsServiceType().getSchema().getCFSecBackingStore().getFactoryServiceType().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecServiceType value ) {
		if( rec != value ) {
			rec = value;
		}
	}

	@Override
	public ICFSecServiceType getServiceTypeRec() {
		return( (ICFSecServiceType)getRec() );
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
	public CFLibDbKeyHash256 getRequiredServiceTypeId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredServiceTypeId(CFLibDbKeyHash256 serviceTypeId) {
		if (getPKey() != serviceTypeId) {
			setPKey(serviceTypeId);
			optionalChildrenDeployed = null;
		}
	}

	@Override
	public String getRequiredDescription() {
		return( getServiceTypeRec().getRequiredDescription() );
	}

	@Override
	public void setRequiredDescription( String value ) {
		if( getServiceTypeRec().getRequiredDescription() != value ) {
			getServiceTypeRec().setRequiredDescription( value );
		}
	}

	@Override
	public List<ICFSecServiceObj> getOptionalChildrenDeployed() {
		List<ICFSecServiceObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().readServiceByTypeIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecServiceObj> getOptionalChildrenDeployed( boolean forceRead ) {
		List<ICFSecServiceObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getServiceTableObj().readServiceByTypeIdx( getPKey(),
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
		ICFSecServiceType origRec = getOrigAsServiceType().getServiceTypeRec();
		ICFSecServiceType myRec = getServiceTypeRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecServiceType origRec = getOrigAsServiceType().getServiceTypeRec();
		ICFSecServiceType myRec = getServiceTypeRec();
		myRec.set( origRec );
	}
}
