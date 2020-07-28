package org.goodiemania.hecate.server.configuration;

public class Rule {
    //This needs at least two things
    //  When to apply this rule, eg, apply to all requests or only a subset
    //  An effect to apply
    //eg
    //  When the request URL contains *115230*
    //  then return this static response+
    //
    //I'm currently debating if these classes should know about fields, or know about fields and *how* to do the thing
    //know about fields means the classes are separated and potentially disconnected?
    //together means messy?
}
