package com.xoftix.xdms.workflow.activiti.specifiers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;

import com.xoftix.xdms.workflow.activiti.commons.CommonBuilder;
import com.xoftix.xdms.workflow.activiti.constants.UtilConstants;
import com.xoftix.xdms.workflow.activiti.util.CampoFiltro;
import com.xoftix.xdms.workflow.activiti.util.ITipo;

public class CommonSpecifications <Entity> {

	private static final Logger LOGGER = Logger.getLogger(CommonSpecifications.class.getName());
	private CommonBuilder builder;

	/**
	 *  Se agrega como no obligatorio porque depende de la implementación 
	 *  es decir para utilizar se debe agreagr la propiedad en el archivo de spring properties
	 *  
	 *  db.jpa.specifications.json = NOMBRE DEL SERVICIO
	 *  */
	@Qualifier("${db.jpa.specifications.json}")
	@Autowired(required = false)
	protected JsonDatabaseFilterSpecifications<Entity> jsonDatabaseFilterSpecifications;
	
	public CommonSpecifications() {
		
	}
	
	public CommonSpecifications(CommonBuilder builder) {
		this.builder = builder;
	}

	public CommonBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CommonBuilder builder) {
		this.builder = builder;
	}
	
	private String containsLowerCase(String searchField) {
		return UtilConstants.WILDCARD + searchField.toLowerCase() + UtilConstants.WILDCARD;
	}
	
	public Specification<Entity> getSpecification(){
		List<Specification<Entity>> listSpecifications = new ArrayList<>();
		try {
			Field[] campos= builder.getClass().getDeclaredFields();
			for (Field field : campos) {
				CampoFiltro campoFiltro = field.getAnnotation(CampoFiltro.class);
				if(campoFiltro!=null) {
					Method method = builder.getClass().getMethod(getterMethodName(field.getName()), new Class[] {});
					Object valor = method.invoke(builder, new Object[] {});
					if(valor!=null) {
						switch (campoFiltro.condicion()) {
						case EQUAL:
							listSpecifications.add(this.filterEqual(campoFiltro, valor));
							break;
						case LIKE:
							listSpecifications.add(this.filterLike(campoFiltro, valor.toString()));
							break;
						case IN:
							listSpecifications.add(this.filterIn(campoFiltro, valor));
							break;
						case SPECIFIED:
							listSpecifications.add(this.filterIsNull(campoFiltro, (boolean)valor));
							break;
						case JSON:
							listSpecifications.add(this.filterJson(campoFiltro, valor));
							break;
						default:
							listSpecifications.add(this.filterEqual(campoFiltro, valor));
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		return this.buildAnd(listSpecifications);
	}

	public Specification<Entity> filterJson(CampoFiltro campoFiltro, Object value) {
		return (root, query, criteriaBuilder) -> {
			Path<?> column = campoFiltro.join().isEmpty()
								? root.get(campoFiltro.campo())
					            : getJoin(root, campoFiltro.join()).get(campoFiltro.campo());
			return this.jsonDatabaseFilterSpecifications
					.filter(criteriaBuilder, column , String.valueOf(value));
		};
	}
	
	public Specification<Entity> filterEqual(CampoFiltro campoFiltro,Object value){
		return (root, query, criteriaBuilder) -> {
			if(campoFiltro.join().isEmpty()) {
				return criteriaBuilder.equal(root.get(campoFiltro.campo()), value);
			}else {
				return criteriaBuilder.equal(getJoin(root, campoFiltro.join()).get(campoFiltro.campo()), value);
			}
		};
	}
	
	public Specification<Entity> filterIsNull(CampoFiltro campoFiltro,boolean value){
		return (root, query, criteriaBuilder) -> {
			if(campoFiltro.join().isEmpty()) {
				return value ? criteriaBuilder.isNotNull(root.get(campoFiltro.campo())) : criteriaBuilder.isNull(root.get(campoFiltro.campo()));
			}else {
				return value ? criteriaBuilder.isNotNull(getJoin(root, campoFiltro.join()).get(campoFiltro.campo())) : criteriaBuilder.isNull(getJoin(root, campoFiltro.join()).get(campoFiltro.campo()));
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public Specification<Entity> filterLike(CampoFiltro campoFiltro,Object value){
		return (root, query, criteriaBuilder) -> {
			if(campoFiltro.join().isEmpty()) {
				if(campoFiltro.concat().isEmpty()) {
					return criteriaBuilder.like(criteriaBuilder.lower(root.get(campoFiltro.campo())), containsLowerCase(value.toString()));
				}else {
					Expression<String> exp1 = criteriaBuilder.concat(criteriaBuilder.lower(root.get(campoFiltro.campo())), " ");
					Expression<String> exp2 = criteriaBuilder.concat(exp1,criteriaBuilder.lower(root.get(campoFiltro.concat())));
					return criteriaBuilder.like(exp2, containsLowerCase(value.toString()));
				}
				
			}else {
				return criteriaBuilder.like(criteriaBuilder.lower(getJoin(root, campoFiltro.join()).get(campoFiltro.campo())), containsLowerCase(value.toString()));
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public Specification<Entity> filterIn(CampoFiltro campoFiltro,Object values){
		return (root, query, criteriaBuilder) -> {
			Collection<Object> listaValores = null;
			if(values instanceof ITipo[]) {
				List<ITipo> listaITipos = Arrays.asList((ITipo[])values);
				listaValores = new LinkedList<>();
				for (ITipo iTipo : listaITipos) {
					listaValores.add(iTipo.getValor());
				}
			}else {
				listaValores = Arrays.asList(values);
			}
			
			if(campoFiltro.join().isEmpty()) {
				return root.get(campoFiltro.campo()).in(listaValores);
			}else {
				return getJoin(root, campoFiltro.join()).get(campoFiltro.campo()).in(listaValores);
			}
		};
	}
	
	public Specification<Entity> buildAnd(List<Specification<Entity>> listSpecifications){
		Specification<Entity> result = null;
		if(listSpecifications != null && !listSpecifications.isEmpty()) {
			result = listSpecifications.get(0);
			for (int i = 1; i < listSpecifications.size(); i++) {
		        result = Specification.where(result).and(listSpecifications.get(i)); //warning 1
		    }
		}
		return result;
	}
	
	private static String getterMethodName(String propertyName) {
        if (null == propertyName || 0 == propertyName.length()) {
            return propertyName;
        }
        StringBuffer sbuff = new StringBuffer(propertyName);
        char firstLetter = sbuff.charAt(0);
        firstLetter = Character.toUpperCase(firstLetter);
        sbuff.setCharAt(0, firstLetter);
        sbuff.insert(0, "get");
        return sbuff.toString();
    }
	
	@SuppressWarnings({ "rawtypes" })
	protected Join getJoin(Root root,String join){
		Join joinEntity = null;
		String[] arraysJoins = join.split("\\.");
		for (String strJoin : arraysJoins) {
			if(joinEntity==null) {
				joinEntity = root.join(strJoin);
			}else {
				joinEntity = joinEntity.join(strJoin);
			}
		}
		return joinEntity;
	}
}
