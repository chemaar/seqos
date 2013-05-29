package org.seerc.seqos.utils;

public enum StatusUtils {

	NORMAL {
                public String toString() {
                        return "obsStatus-A";
                }}, 
                BREAK {
                        public String toString() {
                                return "obsStatus-B";
                }},  
                ESTIMATED {
                        public String toString() {
                                return "obsStatus-E";
                        }},  
                FORECAST {
                        public String toString() {
                                return "obsStatus-F";
                        }}, 
                MISSING {
                                public String toString() {
                                        return "obsStatus-M";
                        }},
                PROVISIONAL  {
                                public String toString() {
                                        return "obsStatus-P";
                        }},  
                STRIKE {
                                public String toString() {
                                return "obsStatus-S";
                }},
                IMPUTED {
                        public String toString() {
                                return "obsStatus-I";
                }},
                NORMALISED {
                        public String toString() {
                                return "obsStatus-N";
                }},
                        
}