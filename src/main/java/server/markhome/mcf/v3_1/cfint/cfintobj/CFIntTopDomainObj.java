// Description: Java 25 base object instance implementation for TopDomain

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

public class CFIntTopDomainObj
	implements ICFIntTopDomainObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFIntTopDomainEditObj edit;
	protected ICFIntSchemaObj schema;
	protected CFLibDbKeyHash256 pKey;
	protected ICFIntTopDomain rec;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFIntTldObj requiredContainerParentTld;
	protected List<ICFIntTopProjectObj> optionalComponentsTopProject;
	protected List<ICFIntLicenseObj> optionalComponentsLicense;

	public CFIntTopDomainObj() {
		isNew = true;
		requiredOwnerTenant = null;
		requiredContainerParentTld = null;
	}

	public CFIntTopDomainObj( ICFIntSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredOwnerTenant = null;
		requiredContainerParentTld = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFIntSchemaObj)schema).getTopDomainTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TopDomain" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFIntTldObj scope = getRequiredContainerParentTld();
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
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().readTopProjectByNameIdx( getRequiredId(),
				nextName, false );
		}
		if( subObj == null ) {
			subObj = ((ICFIntSchemaObj)getSchema()).getLicenseTableObj().readLicenseByUNameIdx( getRequiredId(),
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
	public ICFIntTopDomainObj realise() {
		ICFIntTopDomainObj retobj = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().realiseTopDomain(
			(ICFIntTopDomainObj)this );
		return( (ICFIntTopDomainObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().reallyDeepDisposeTopDomain( (ICFIntTopDomainObj)this );
	}

	@Override
	public ICFIntTopDomainObj read() {
		ICFIntTopDomainObj retobj = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByIdIdx( getPKey(), false );
		return( (ICFIntTopDomainObj)retobj );
	}

	@Override
	public ICFIntTopDomainObj read( boolean forceRead ) {
		ICFIntTopDomainObj retobj = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByIdIdx( getPKey(), forceRead );
		return( (ICFIntTopDomainObj)retobj );
	}

	@Override
	public ICFIntTopDomainTableObj getTopDomainTable() {
		return( ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj() );
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
		schema = value;
	}

	@Override
	public ICFIntTopDomain getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFIntBackingStore().getFactoryTopDomain().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFIntBackingStore().getTableTopDomain().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntTopDomain value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFIntTopDomain ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFIntTopDomainRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredOwnerTenant = null;
		requiredContainerParentTld = null;
	}

	@Override
	public ICFIntTopDomain getTopDomainRec() {
		return( (ICFIntTopDomain)getRec() );
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
	public ICFIntTopDomainEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFIntTopDomainObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFIntTopDomainObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().lockTopDomain( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().newEditInstance( lockobj );
		return( (ICFIntTopDomainEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFIntTopDomainEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFIntTopDomainEditObj getEditAsTopDomain() {
		return( (ICFIntTopDomainEditObj)edit );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFIntTopDomain rec = getRec();
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
			ICFIntTopDomain rec = getRec();
			updatedBy = ((ICFIntSchemaObj)getSchema()).getSecUserTableObj().readSecUserByIdIdx( rec.getUpdatedByUserId() );
		}
		return( updatedBy );
	}

	@Override
	public LocalDateTime getUpdatedAt() {
		return( getRec().getUpdatedAt() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredId() {
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
				requiredOwnerTenant = ((ICFIntSchemaObj)getSchema()).getTenantTableObj().readTenantByIdIdx( getTopDomainRec().getRequiredTenantId(), forceRead );
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public ICFIntTldObj getRequiredContainerParentTld() {
		return( getRequiredContainerParentTld( false ) );
	}

	@Override
	public ICFIntTldObj getRequiredContainerParentTld( boolean forceRead ) {
		if( ( requiredContainerParentTld == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerParentTld = ((ICFIntSchemaObj)getSchema()).getTldTableObj().readTldByIdIdx( getTopDomainRec().getRequiredTldId(), forceRead );
			}
		}
		return( requiredContainerParentTld );
	}

	@Override
	public List<ICFIntTopProjectObj> getOptionalComponentsTopProject() {
		List<ICFIntTopProjectObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().readTopProjectByTopDomainIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFIntTopProjectObj> getOptionalComponentsTopProject( boolean forceRead ) {
		List<ICFIntTopProjectObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().readTopProjectByTopDomainIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFIntLicenseObj> getOptionalComponentsLicense() {
		List<ICFIntLicenseObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getLicenseTableObj().readLicenseByDomainIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFIntLicenseObj> getOptionalComponentsLicense( boolean forceRead ) {
		List<ICFIntLicenseObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getLicenseTableObj().readLicenseByDomainIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTopDomainRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTldId() {
		return( getTopDomainRec().getRequiredTldId() );
	}

	@Override
	public String getRequiredName() {
		return( getTopDomainRec().getRequiredName() );
	}

	@Override
	public String getOptionalDescription() {
		return( getTopDomainRec().getOptionalDescription() );
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
