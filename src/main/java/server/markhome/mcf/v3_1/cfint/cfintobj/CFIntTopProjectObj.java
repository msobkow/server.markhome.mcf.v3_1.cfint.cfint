// Description: Java 25 base object instance implementation for TopProject

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

public class CFIntTopProjectObj
	implements ICFIntTopProjectObj
{
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected boolean isNew;
	protected ICFIntTopProjectEditObj edit;
	protected ICFIntSchemaObj schema;
	protected CFLibDbKeyHash256 pKey;
	protected ICFIntTopProject rec;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFIntTopDomainObj requiredContainerParentSDom;
	protected List<ICFIntSubProjectObj> optionalComponentsSubProject;

	public CFIntTopProjectObj() {
		isNew = true;
		requiredOwnerTenant = null;
		requiredContainerParentSDom = null;
	}

	public CFIntTopProjectObj( ICFIntSchemaObj argSchema ) {
		schema = argSchema;
		isNew = true;
		edit = null;
		requiredOwnerTenant = null;
		requiredContainerParentSDom = null;
	}

	@Override
	public int getClassCode() {
		return( ((ICFIntSchemaObj)schema).getTopProjectTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "TopProject" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFIntTopDomainObj scope = getRequiredContainerParentSDom();
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
			subObj = ((ICFIntSchemaObj)getSchema()).getSubProjectTableObj().readSubProjectByNameIdx( getRequiredId(),
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
	public ICFIntTopProjectObj realise() {
		ICFIntTopProjectObj retobj = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().realiseTopProject(
			(ICFIntTopProjectObj)this );
		return( (ICFIntTopProjectObj)retobj );
	}

	@Override
	public void forget() {
		((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().reallyDeepDisposeTopProject( (ICFIntTopProjectObj)this );
	}

	@Override
	public ICFIntTopProjectObj read() {
		ICFIntTopProjectObj retobj = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().readTopProjectByIdIdx( getPKey(), false );
		return( (ICFIntTopProjectObj)retobj );
	}

	@Override
	public ICFIntTopProjectObj read( boolean forceRead ) {
		ICFIntTopProjectObj retobj = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().readTopProjectByIdIdx( getPKey(), forceRead );
		return( (ICFIntTopProjectObj)retobj );
	}

	@Override
	public ICFIntTopProjectTableObj getTopProjectTable() {
		return( ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj() );
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
	public ICFIntTopProject getRec() {
		if( rec == null ) {
			if( isNew ) {
				rec = getSchema().getCFIntBackingStore().getFactoryTopProject().newRec();
			}
			else {
				// Read the data rec via the backing store
				rec = getSchema().getCFIntBackingStore().getTableTopProject().readDerivedByIdIdx( ((ICFIntSchemaObj)getSchema()).getAuthorization(),
						getPKey() );
				if( rec != null ) {
					copyRecToPKey();
				}
			}
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntTopProject value ) {
		if( ! ( ( value == null ) || ! ( value instanceof ICFIntTopProject ) ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				"setRec",
				"value",
				value,
				"CFIntTopProjectRec" );
		}
		rec = value;
		copyRecToPKey();
		requiredOwnerTenant = null;
		requiredContainerParentSDom = null;
	}

	@Override
	public ICFIntTopProject getTopProjectRec() {
		return( (ICFIntTopProject)getRec() );
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
	public ICFIntTopProjectEditObj beginEdit() {
		if( edit != null ) {
			throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
		}
		ICFIntTopProjectObj lockobj;
		if( getIsNew() ) {
			lockobj = (ICFIntTopProjectObj)this;
		}
		else {
			lockobj = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().lockTopProject( getPKey() );
		}
		edit = ((ICFIntSchemaObj)getSchema()).getTopProjectTableObj().newEditInstance( lockobj );
		return( (ICFIntTopProjectEditObj)edit );
	}

	@Override
	public void endEdit() {
		edit = null;
	}

	@Override
	public ICFIntTopProjectEditObj getEdit() {
		return( edit );
	}

	@Override
	public ICFIntTopProjectEditObj getEditAsTopProject() {
		return( (ICFIntTopProjectEditObj)edit );
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFIntTopProject rec = getRec();
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
			ICFIntTopProject rec = getRec();
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
				requiredOwnerTenant = ((ICFIntSchemaObj)getSchema()).getTenantTableObj().readTenantByIdIdx( getTopProjectRec().getRequiredTenantId(), forceRead );
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerParentSDom() {
		return( getRequiredContainerParentSDom( false ) );
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerParentSDom( boolean forceRead ) {
		if( ( requiredContainerParentSDom == null ) || forceRead ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				requiredContainerParentSDom = ((ICFIntSchemaObj)getSchema()).getTopDomainTableObj().readTopDomainByIdIdx( getTopProjectRec().getRequiredTopDomainId(), forceRead );
			}
		}
		return( requiredContainerParentSDom );
	}

	@Override
	public List<ICFIntSubProjectObj> getOptionalComponentsSubProject() {
		List<ICFIntSubProjectObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSubProjectTableObj().readSubProjectByTopProjectIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFIntSubProjectObj> getOptionalComponentsSubProject( boolean forceRead ) {
		List<ICFIntSubProjectObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSubProjectTableObj().readSubProjectByTopProjectIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTenantId() {
		return( getTopProjectRec().getRequiredTenantId() );
	}

	@Override
	public CFLibDbKeyHash256 getRequiredTopDomainId() {
		return( getTopProjectRec().getRequiredTopDomainId() );
	}

	@Override
	public String getRequiredName() {
		return( getTopProjectRec().getRequiredName() );
	}

	@Override
	public String getOptionalDescription() {
		return( getTopProjectRec().getOptionalDescription() );
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
