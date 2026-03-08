// Description: Java 25 edit object instance implementation for CFInt SecGroup.

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

public class CFIntSecGroupEditObj
	implements ICFIntSecGroupEditObj
{
	protected ICFSecSecGroupObj orig;
	protected ICFSecSecGroup rec;
	protected ICFSecSecUserObj createdBy = null;
	protected ICFSecSecUserObj updatedBy = null;
	protected ICFSecClusterObj requiredContainerCluster;
	protected List<ICFSecSecGrpIncObj> optionalComponentsInclude;
	protected List<ICFSecSecGrpMembObj> optionalComponentsMember;
	protected List<ICFSecSecGrpIncObj> requiredChildrenIncByGroup;

	public CFIntSecGroupEditObj( ICFSecSecGroupObj argOrig ) {
		orig = argOrig;
		getRec();
		ICFSecSecGroup origRec = orig.getRec();
		rec.set( origRec );
		requiredContainerCluster = null;
	}

	@Override
	public ICFSecSecUserObj getCreatedBy() {
		if( createdBy == null ) {
			ICFSecSecGroup rec = getRec();
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
			ICFSecSecGroup rec = getRec();
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
		return( ((ICFSecSchemaObj)orig.getSchema()).getSecGroupTableObj().getClassCode() );
	}

	@Override
	public String getGenDefName() {
		return( "SecGroup" );
	}

	@Override
	public ICFLibAnyObj getObjScope() {
		ICFSecClusterObj scope = getRequiredContainerCluster();
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
	public ICFSecSecGroupObj realise() {
		// We realise this so that it's record will get copied to orig during realization
		ICFSecSecGroupObj retobj = getSchema().getSecGroupTableObj().realiseSecGroup( (ICFIntSecGroupObj)this );
		return( retobj );
	}

	@Override
	public void forget() {
		getOrigAsSecGroup().forget();
	}

	@Override
	public ICFSecSecGroupObj read() {
		ICFSecSecGroupObj retval = getOrigAsSecGroup().read();
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGroupObj read( boolean forceRead ) {
		ICFSecSecGroupObj retval = getOrigAsSecGroup().read( forceRead );
		if( retval != orig ) {
			throw new CFLibStaleCacheDetectedException( getClass(),	"read" );
		}
		copyOrigToRec();
		return( retval );
	}

	@Override
	public ICFSecSecGroupObj create() {
		copyRecToOrig();
		ICFSecSecGroupObj retobj = ((ICFIntSchemaObj)getOrigAsSecGroup().getSchema()).getSecGroupTableObj().createSecGroup( getOrigAsSecGroup() );
		if( retobj == getOrigAsSecGroup() ) {
			copyOrigToRec();
		}
		return( retobj );
	}

	@Override
	public CFSecSecGroupEditObj update() {
		getSchema().getSecGroupTableObj().updateSecGroup( (ICFSecSecGroupObj)this );
		return( null );
	}

	@Override
	public CFSecSecGroupEditObj deleteInstance() {
		if( getIsNew() ) {
			throw new CFLibCannotDeleteNewInstanceException( getClass(), "delete" );
		}
		getSchema().getSecGroupTableObj().deleteSecGroup( getOrigAsSecGroup() );
		return( null );
	}

	@Override
	public ICFSecSecGroupTableObj getSecGroupTable() {
		return( orig.getSchema().getSecGroupTableObj() );
	}

	@Override
	public ICFSecSecGroupEditObj getEdit() {
		return( (ICFSecSecGroupEditObj)this );
	}

	@Override
	public ICFSecSecGroupEditObj getEditAsSecGroup() {
		return( (ICFSecSecGroupEditObj)this );
	}

	@Override
	public ICFSecSecGroupEditObj beginEdit() {
		throw new CFLibEditAlreadyOpenException( getClass(), "beginEdit" );
	}

	@Override
	public void endEdit() {
		orig.endEdit();
	}

	@Override
	public ICFSecSecGroupObj getOrig() {
		return( orig );
	}

	@Override
	public ICFSecSecGroupObj getOrigAsSecGroup() {
		return( (ICFSecSecGroupObj)orig );
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
	public ICFSecSecGroup getRec() {
		if( rec == null ) {
			rec = getOrigAsSecGroup().getSchema().getCFSecBackingStore().getFactorySecGroup().newRec();
			rec.set( orig.getRec() );
		}
		return( rec );
	}

	@Override
	public void setRec( ICFSecSecGroup value ) {
		if( rec != value ) {
			rec = value;
			requiredContainerCluster = null;
		}
	}

	@Override
	public ICFSecSecGroup getSecGroupRec() {
		return( (ICFSecSecGroup)getRec() );
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
	public CFLibDbKeyHash256 getRequiredSecGroupId() {
		return( getPKey() );
	}

	@Override
	public void setRequiredSecGroupId(CFLibDbKeyHash256 secGroupId) {
		if (getPKey() != secGroupId) {
			setPKey(secGroupId);
			requiredContainerCluster = null;
			optionalComponentsInclude = null;
			optionalComponentsMember = null;
			requiredChildrenIncByGroup = null;
		}
	}

	@Override
	public CFLibDbKeyHash256 getRequiredClusterId() {
		return( getSecGroupRec().getRequiredClusterId() );
	}

	@Override
	public String getRequiredName() {
		return( getSecGroupRec().getRequiredName() );
	}

	@Override
	public void setRequiredName( String value ) {
		if( getSecGroupRec().getRequiredName() != value ) {
			getSecGroupRec().setRequiredName( value );
		}
	}

	@Override
	public boolean getRequiredIsVisible() {
		return( getSecGroupRec().getRequiredIsVisible() );
	}

	@Override
	public void setRequiredIsVisible( boolean value ) {
		if( getSecGroupRec().getRequiredIsVisible() != value ) {
			getSecGroupRec().setRequiredIsVisible( value );
		}
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster() {
		return( getRequiredContainerCluster( false ) );
	}

	@Override
	public ICFSecClusterObj getRequiredContainerCluster( boolean forceRead ) {
		if( forceRead || ( requiredContainerCluster == null ) ) {
			boolean anyMissing = false;
			if( ! anyMissing ) {
				ICFSecClusterObj obj = ((ICFIntSchemaObj)getOrigAsSecGroup().getSchema()).getClusterTableObj().readClusterByIdIdx( getSecGroupRec().getRequiredClusterId() );
				requiredContainerCluster = obj;
				if( obj != null ) {
					requiredContainerCluster = obj;
				}
			}
		}
		return( requiredContainerCluster );
	}

	@Override
	public void setRequiredContainerCluster( ICFSecClusterObj value ) {
		if( rec == null ) {
			getSecGroupRec();
		}
		if( value != null ) {
			requiredContainerCluster = value;
			getSecGroupRec().setRequiredContainerCluster(value.getClusterRec());
		}
		requiredContainerCluster = value;
	}

	@Override
	public List<ICFSecSecGrpIncObj> getOptionalComponentsInclude() {
		List<ICFSecSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpIncTableObj().readSecGrpIncByGroupIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpIncObj> getOptionalComponentsInclude( boolean forceRead ) {
		List<ICFSecSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpIncTableObj().readSecGrpIncByGroupIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpMembObj> getOptionalComponentsMember() {
		List<ICFSecSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByGroupIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpMembObj> getOptionalComponentsMember( boolean forceRead ) {
		List<ICFSecSecGrpMembObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpMembTableObj().readSecGrpMembByGroupIdx( getPKey(),
			forceRead );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpIncObj> getRequiredChildrenIncByGroup() {
		List<ICFSecSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpIncTableObj().readSecGrpIncByIncludeIdx( getPKey(),
			false );
		return( retval );
	}

	@Override
	public List<ICFSecSecGrpIncObj> getRequiredChildrenIncByGroup( boolean forceRead ) {
		List<ICFSecSecGrpIncObj> retval;
		retval = ((ICFIntSchemaObj)getSchema()).getSecGrpIncTableObj().readSecGrpIncByIncludeIdx( getPKey(),
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
		ICFSecSecGroup origRec = getOrigAsSecGroup().getSecGroupRec();
		ICFSecSecGroup myRec = getSecGroupRec();
		origRec.set( myRec );
	}

	@Override
	public void copyOrigToRec() {
		ICFSecSecGroup origRec = getOrigAsSecGroup().getSecGroupRec();
		ICFSecSecGroup myRec = getSecGroupRec();
		myRec.set( origRec );
	}
}
