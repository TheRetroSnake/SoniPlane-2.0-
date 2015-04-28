package soni.plane.gs.tools;

import java.util.ArrayList;

public class ConfigFile {
    /* permission types */
    public static final int READ =  1;
    public static final int WRITE = 2;

    /* the file this is stored in */
    private String file;
    /* permissions given for read/write operations */
    private int perms;

    /* sections this file has */
    private ArrayList<Section> sect;
    /* sections this file has */
    private ArrayList<Field> field;

    public ConfigFile(String file, String name, int perms, String data) {
        this.file = file +"!"+ name;
        this.perms = perms;

        resolveData(data);
    }

    public ConfigFile(String file, int perms, String data) {
        this.file = file;
        this.perms = perms;

        resolveData(data);
    }

    public ConfigFile(String file, int perms) {
        this.file = file;
        this.perms = perms;
    }

    /* gets all the data from supplied String and puts into proper arrays */
    private void resolveData(String data) {
        /* create new ArrayLists for filling it later */
		field = new ArrayList<Field>();
		sect = new ArrayList<Section>();

		/* variables used in the loop */
		String tempName = null;
		String section = "";
        /* loop for all the lines in this String */
        for(String line : data.replace("\r", "").split("\n")){
            /* if no section found, search for one */
			if(tempName == null) {
				if (line.startsWith("#") && line.endsWith(" {")) {
					tempName = line.replace("#", "").replace(" {", "");

				} else if (line.contains(": ")) {
                	/* if regular field, add it */
					field.add(new Field(line.split(": ")[0], line.split(": ")[1]));
				}
			} else {
			/* checking for section */
				if(line.equals("}")){
					/* section ended, create section with set data */
					sect.add(new Section(tempName, section.length() > 0 ? section.substring(0, section.length() -1) : ""));
					/* clear set variables */
					tempName = null;
					section = "";
				} else {
					/* add line data and newline */
					section += line.substring(1) +"\n";
				}
			}
		}

		/* create new section if last one was being recorded */
		if(tempName != null){
			sect.add(new Section(tempName, section.length() > 0 ? section.substring(0, section.length() -1) : ""));
		}
    }

    /* check if lists include field
     * permissions: READ */
    public boolean containsField(String field){
        return getField_(field) != null;
    }

    /* gets target field (if exists), else sends warning
     * permissions: READ */
    public Field getField(String field) {
        /* check this can be read from */
        if (!hasPermission(READ)) {
            permissionError(READ);
            return null;
        }

        /* get the actual field */
        Field f = getField_(field);

        /* if field is not null, return it as normal */
        if (f != null) {
            return f;
        }

        /* else warn and return null */
        System.err.println(this.getClass() + ": Warning! Field '" + field + "' not found in '" + file +"'");
        return null;
    }

    /* gets target field (if exists)
     * permissions: READ */
    private Field getField_(String field){
        /* check this can be read from */
        if(hasPermission(READ)) {
            /* loop for all fields */
            for (Field f : this.field) {
                /* if field name equals given name */
                if (f.getName().equals(field)) {
                    return f;
                }
            }
        }

        return null;
    }

    /* sets target field (if exists) to a value, or creates new one if does not exist. If fails, sends warning
     * permissions: WRITE */
    public Field setField(String field, String value) {
        /* check this can be written to */
        if (!hasPermission(WRITE)) {
            permissionError(WRITE);
            return null;
        }

        /* set the actual field */
        Field f = setField_(field, value);

        /* if field is not null, return it as normal */
        if (f != null) {
            return f;
        }

        /* else warn and return null */
        System.err.println(this.getClass() + ": Warning! Field '" + field + "' could not be created to '" + file +"'");
        return null;
    }

    /* gets target field (if exists) to a value, or creates new one if does not exist.
     * permissions: WRITE */
    private Field setField_(String field, String value){
        /* check this can be written to */
        if(hasPermission(WRITE)) {
            /* loop for all fields */
            for (Field f : this.field) {
                /* if field name equals given name */
                if (f.getName().equals(field)) {

                    /* set value and return */
                    f.setValue(value);
                    return f;
                }
            }

            /* failed to find target field. Create a new one. */
            Field f = new Field(field, value);
            /* add field to memory and return it */
            this.field.add(f);
            return f;
        }

        return null;
    }

    /* removes target field. If fails, sends error
     * permissions: WRITE */
    public boolean rmvField(String field) {
        /* check this can be written to */
        if (!hasPermission(WRITE)) {
            permissionError(WRITE);
            return false;
        }

        /* set the actual field */
        boolean b = rmvField_(field);

        /* if field is not null, return it as normal */
        if (b) {
            return b;
        }

        /* else warn and return null */
        System.err.println(this.getClass() + ": Warning! Field '" + field + "' could not be removed from '" + file +"'");
        return false;
    }

    /* gets target field (if exists) to a value, or creates new one if does not exist.
     * permissions: WRITE */
    private boolean rmvField_(String field){
        /* check this can be written to */
        if(hasPermission(WRITE)) {
            /* loop for all fields */
            for (Field f : this.field) {
                /* if field name equals given name */
                if (f.getName().equals(field)) {

                    /* remove and return */
                    this.field.remove(f);
                    return true;
                }
            }
        }

        return false;
    }

    /* check if has correct permission level */
    private boolean hasPermission(int perm) {
        return (perms & perm) != 0;
    }

    /* check if can be written to */
    public boolean canWrite(){
        return hasPermission(WRITE);
    }

    /* check if can be read from */
    public boolean canRead(){
        return hasPermission(READ);
    }

    /* gets the file this is stored in */
    public String getFile(){
        return file;
    }

    /* send insufficient permissions warning to console */
    private void permissionError(int req) {
        System.err.println(this.getClass() + ": Warning! permissions not sufficient! Required permissions: "+ getPermString(req));
    }

    /* returns String representation of what permissions are needed */
    private String getPermString(int req) {
        switch (req){
            case READ:
                return "READ";

            case WRITE:
                return "WRITE";

            case READ | WRITE:
                return "READ & WRITE";
        }
        return "ILLEGAL PERMISSIONS";
    }

    public class Section {
		/* name given to this section */
		private final String name;
		/* fields it contains */
		private ArrayList<Field> field;
		/* sections it contains */
		private ArrayList<Section> sect;

		public Section(String name, String data){
			this.name = name;
			resolve(data);
		}

		public Section(String name){
			this.name = name;
			field = new ArrayList<Field>();
			sect = new ArrayList<Section>();
		}

		private void resolve(String data) {
			/* create new ArrayLists for filling it later */
			field = new ArrayList<Field>();
			sect = new ArrayList<Section>();

			/* variables used in the loop */
			String tempName = null;
			String section = "";
       		/* loop for all the lines in this String */
			for(String line : data.replace("\r", "").split("\n")){
            	/* if no section found, search for one */
				if(tempName == null) {
					if (line.startsWith("#") && line.endsWith(" {")) {
						tempName = line.replace("#", "").replace(" {", "");

					} else if (line.contains(": ")) {
                		/* if regular field, add it */
						field.add(new Field(line.split(": ")[0], line.split(": ")[1]));
					}
				} else {
				/* checking for section */
					if(line.equals("}")){
						/* section ended, create section with set data */
						sect.add(new Section(tempName, section.length() > 0 ? section.substring(0, section.length() -1) : ""));
						/* clear set variables */
						tempName = null;
						section = "";
					} else {
						/* add line data and newline */
						section += line.substring(1) +"\n";
					}
				}
			}

			/* create new section if last one was being recorded */
			if(tempName != null){
				sect.add(new Section(tempName, section.length() > 0 ? section.substring(0, section.length() -1) : ""));
			}
		}
	}

    public class Field {
        private final String name;
        private String value;

        public Field(String name, String value){
            this.name = name;
            this.value = value;
        }

        public void setValue(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }

        public String getName(){
            return name;
        }
    }
}
