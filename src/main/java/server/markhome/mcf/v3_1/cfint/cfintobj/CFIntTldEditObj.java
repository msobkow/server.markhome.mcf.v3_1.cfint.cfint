// Description: Java 25 edit object instance implementation for CFInt Tld.

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

public class CFIntTldEditObj
	implements ICFIntTldEditObj
{
	protected ICFIntTldObj orig;
	protected ICFIntTld rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredContainerTenant;
	protected List<ICFIntTopDomainObj> optionalComponentsTopDomain;

	public CFIntTldEditObj( ICFIntTldObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFIntTld origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerTenant = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFIntTld rec = getRec();
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
			ICFIntTld rec = getRec();
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
		return( ((ICFIntSchemaObj)orig.getSchema()).getTldTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "Tld" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		return( getRequiredContainerTenant() );
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
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByNameIdx( getRequiredId(),
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
	public ICFIntTldObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFIntTldObj retobj = getSchema().getTldTableObj().realiseTld( (ICFIntTldObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTld().forget();
	}

	@Override
	public ICFIntTldObj read() {
		ICFIntTldObj retval = getOrigAsTld().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntTldObj read( boolean forceRead ) {
		ICFIntTldObj retval = getOrigAsTld().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntTldObj create() {
		copyRecToOrig();
		ICFIntTldObj retobj = ((ICFIntSchemaObj)getOrigAsTld().getSchema()).getTldTableObj().createTld( getOrigAsTld() );
		if( retobj == getOrigAsTld() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFIntTldEditObj update() {
		getSchema().getTldTableObj().updateTld( (ICFIntTldObj)this );
		return( null );
	}

	@Override
	public CFIntTldEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTldTableObj().deleteTld( getOrigAsTld() );
		return( null );
	}

	@Override
	public ICFIntTldTableObj getTldTable() {
		return( orig.getSchema().getTldTableObj() );
	}

	@Override
	public ICFIntTldEditObj getEdit() {
		return( (ICFIntTldEditObj)this );
	}

	@Override
	public ICFIntTldEditObj getEditAsTld() {
		return( (ICFIntTldEditObj)this );
	}

	@Override
	public ICFIntTldEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFIntTldObj getOrig() {
		return( orig );
	}

	@Override
	public ICFIntTldObj getOrigAsTld() {
		return( (ICFIntTldObj)orig );
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
	public ICFIntTld getRec() {
		if( rec == null ) {
			rec = getOrigAsTld().getSchema().getCFIntBackingStore().getFactoryTld().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntTld value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerTenant = null;
		}
	}

	@Override
	public ICFIntTld getTldRec() {
		return( (ICFIntTld)getRec() );
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
			requiredContainerTenant = null;
			optionalComponentsTopDomain = null;
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTldRec().getRequiredTenantId() );
	}

	@Override
	public String getRequiredName() {
		return( getTldRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getTldRec().getRequiredName() != value ) {
			getTldRec().setRequiredName( value );
		}
	}

	@Override
	public String getOptionalDescription() {
		return( getTldRec().getOptionalDescription() );
	}

	@Override
	public void setOptionalDescription( String value ) {
		if( getTldRec().getOptionalDescription() != value ) {
			getTldRec().setOptionalDescription( value );
		}
	}

	@Override
	public ICFSecTenantObj getRequiredContainerTenant() {
		return( getRequiredContainerTenant( false ) );
	}

	@Override
	public ICFSecTenantObj getRequiredContainerTenant( boolean forceRead ) {
		if( forceRead || ( requiredContainerTenant == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsTld().getSchema()).getTenantTableObj().readTenantByIdIdx( getTldRec().getRequiredTenantId() );
				requiredContainerTenant = obj;
				if( obj != null ) {
					requiredContainerTenant = obj;
				}
			}
		}
		return( requiredContainerTenant );
	}

	@Override
	public void setRequiredContainerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getTldRec();
		}
		if( value != null ) {
			requiredContainerTenant = value;
			getTldRec().setRequiredContainerTenant(value.getTenantRec());
		}
		requiredContainerTenant = value;
	}

	@Override
	public List<ICFIntTopDomainObj> getOptionalComponentsTopDomain() {
		List<ICFIntTopDomainObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByTldIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFIntTopDomainObj> getOptionalComponentsTopDomain( boolean forceRead ) {
		List<ICFIntTopDomainObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByTldIdx( getPKey(),
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
		ICFIntTld origRec = getOrigAsTld().getTldRec();
		ICFIntTld myRec = getTldRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFIntTld origRec = getOrigAsTld().getTldRec();
		ICFIntTld myRec = getTldRec();
		myRec.set( origRec );
	}
}
