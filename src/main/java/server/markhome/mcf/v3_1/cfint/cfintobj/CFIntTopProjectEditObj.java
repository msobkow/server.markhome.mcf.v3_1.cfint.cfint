// Description: Java 25 edit object instance implementation for CFInt TopProject.

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

public class CFIntTopProjectEditObj
	implements ICFIntTopProjectEditObj
{
	protected ICFIntTopProjectObj orig;
	protected ICFIntTopProject rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecTenantObj requiredOwnerTenant;
	protected ICFIntTopDomainObj requiredContainerParentSDom;
	protected List<ICFIntSubProjectObj> optionalComponentsSubProject;

	public CFIntTopProjectEditObj( ICFIntTopProjectObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFIntTopProject origRec = orig.getRec();
		rec.set( origRec );
		requiredOwnerTenant = null;
		requiredContainerParentSDom = null;
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
		return( ((ICFIntSchemaObj)orig.getSchema()).getTopProjectTableObj().getClassCode() );
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
		// We realise this so that it's record will get copied to orig during realization
		ICFIntTopProjectObj retobj = getSchema().getTopProjectTableObj().realiseTopProject( (ICFIntTopProjectObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsTopProject().forget();
	}

	@Override
	public ICFIntTopProjectObj read() {
		ICFIntTopProjectObj retval = getOrigAsTopProject().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntTopProjectObj read( boolean forceRead ) {
		ICFIntTopProjectObj retval = getOrigAsTopProject().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFIntTopProjectObj create() {
		copyRecToOrig();
		ICFIntTopProjectObj retobj = ((ICFIntSchemaObj)getOrigAsTopProject().getSchema()).getTopProjectTableObj().createTopProject( getOrigAsTopProject() );
		if( retobj == getOrigAsTopProject() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFIntTopProjectEditObj update() {
		getSchema().getTopProjectTableObj().updateTopProject( (ICFIntTopProjectObj)this );
		return( null );
	}

	@Override
	public CFIntTopProjectEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getTopProjectTableObj().deleteTopProject( getOrigAsTopProject() );
		return( null );
	}

	@Override
	public ICFIntTopProjectTableObj getTopProjectTable() {
		return( orig.getSchema().getTopProjectTableObj() );
	}

	@Override
	public ICFIntTopProjectEditObj getEdit() {
		return( (ICFIntTopProjectEditObj)this );
	}

	@Override
	public ICFIntTopProjectEditObj getEditAsTopProject() {
		return( (ICFIntTopProjectEditObj)this );
	}

	@Override
	public ICFIntTopProjectEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFIntTopProjectObj getOrig() {
		return( orig );
	}

	@Override
	public ICFIntTopProjectObj getOrigAsTopProject() {
		return( (ICFIntTopProjectObj)orig );
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
	public ICFIntTopProject getRec() {
		if( rec == null ) {
			rec = getOrigAsTopProject().getSchema().getCFIntBackingStore().getFactoryTopProject().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFIntTopProject value ) {
		if( rec != value ) {
			rec = value;
			requiredOwnerTenant = null;
			requiredContainerParentSDom = null;
		}
	}

	@Override
	public ICFIntTopProject getTopProjectRec() {
		return( (ICFIntTopProject)getRec() );
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
			requiredOwnerTenant = null;
			requiredContainerParentSDom = null;
			optionalComponentsSubProject = null;
		}
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
	public void setRequiredName( String value ) {
		if( getTopProjectRec().getRequiredName() != value ) {
			getTopProjectRec().setRequiredName( value );
		}
	}

	@Override
	public String getOptionalDescription() {
		return( getTopProjectRec().getOptionalDescription() );
	}

	@Override
	public void setOptionalDescription( String value ) {
		if( getTopProjectRec().getOptionalDescription() != value ) {
			getTopProjectRec().setOptionalDescription( value );
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
				ICFSecTenantObj obj = ((ICFIntSchemaObj)getOrigAsTopProject().getSchema()).getTenantTableObj().readTenantByIdIdx( getTopProjectRec().getRequiredTenantId() );
				requiredOwnerTenant = obj;
			}
		}
		return( requiredOwnerTenant );
	}

	@Override
	public void setRequiredOwnerTenant( ICFSecTenantObj value ) {
		if( rec == null ) {
			getTopProjectRec();
		}
		if( value != null ) {
			requiredOwnerTenant = value;
			getTopProjectRec().setRequiredOwnerTenant(value.getTenantRec());
		}
		requiredOwnerTenant = value;
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerParentSDom() {
		return( getRequiredContainerParentSDom( false ) );
	}

	@Override
	public ICFIntTopDomainObj getRequiredContainerParentSDom( boolean forceRead ) {
		if( forceRead || ( requiredContainerParentSDom == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFIntTopDomainObj obj = ((ICFIntSchemaObj)getOrigAsTopProject().getSchema()).getTopDomainTableObj().readTopDomainByIdIdx( getTopProjectRec().getRequiredTopDomainId() );
				requiredContainerParentSDom = obj;
				if( obj != null ) {
					requiredContainerParentSDom = obj;
				}
			}
		}
		return( requiredContainerParentSDom );
	}

	@Override
	public void setRequiredContainerParentSDom( ICFIntTopDomainObj value ) {
		if( rec == null ) {
			getTopProjectRec();
		}
		if( value != null ) {
			requiredContainerParentSDom = value;
			getTopProjectRec().setRequiredContainerParentSDom(value.getTopDomainRec());
		}
		requiredContainerParentSDom = value;
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
		ICFIntTopProject origRec = getOrigAsTopProject().getTopProjectRec();
		ICFIntTopProject myRec = getTopProjectRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFIntTopProject origRec = getOrigAsTopProject().getTopProjectRec();
		ICFIntTopProject myRec = getTopProjectRec();
		myRec.set( origRec );
	}
}
