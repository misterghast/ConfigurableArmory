package org.hypbase.configureablearmory.mixin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.MethodNode;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.tools.nsc.transform.SpecializeTypes;

public class CAClassTransformer implements IClassTransformer {
    Logger logger = LogManager.getLogger("ConfigurableArmory");

    public CAClassTransformer() {

    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(transformedName.equals("net.minecraft.client.Minecraft")) {
            return patch(basicClass);
        } else {

        }
        return basicClass;
    }

    private byte[] patch(byte[] basicClass) {
        //basically stolen from ResourceLoader
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        //I know this is just for debug messages but just leave me alone ok I haven't done ASM before
        logger.log(Level.DEBUG, "Found Minecraft Class: " + classNode.name);

        MethodNode refreshResources = null;
        MethodNode startGame = null;

        for(Object mn : classNode.methods) {
            if(mn instanceof MethodNode) {
                MethodNode mmn = (MethodNode) mn;
                if((mmn.name.equals("f") && mmn.desc.equals("()V")) || mmn.name.equals("refreshResources")) {
                    refreshResources = mmn;
                } else if(mmn.name.equals(MCPNaming.method("func_71384_a"))) {
                    startGame = mmn;
                }
            }
        }

        if (refreshResources != null) {
            logger.log(Level.DEBUG, " - Found refreshResources 1/3");

            for(int i = 0; i < refreshResources.instructions.size(); i++) {
                AbstractInsnNode ain = refreshResources.instructions.get(i);
                if(ain instanceof MethodInsnNode) {
                    MethodInsnNode min = (MethodInsnNode) ain;
                    if((min.name.equals("a") && min.desc.equals("(Ljava/util/List;)V"))|| min.name.equals("reloadResources")) {
                        InsnList toInsert = new InsnList();
                        toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/hypbase/configureablearmory/ConfigurableArmory", "insertFPack", "(Ljava/util/List;)V", false));
                        toInsert.add(new VarInsnNode(Opcodes.ALOAD, 1));

                        refreshResources.instructions.insertBefore(min, toInsert);
                        logger.log(Level.DEBUG, " - Patched refreshResources 3/3");

                        i+=2;
                    /*} else if(min.name.equals("newArrayList")) {
                        AbstractInsnNode target = refreshResources.instructions.get(i + 1);

                        InsnList toInsert = new InsnList();
                        toInsert.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/hypbase/configureablearmory/ConfigurableArmory", "insertPack", "(Ljava/util/List;)V", false));

                        refreshResources.instructions.insert(target, toInsert);
                        logger.log(Level.DEBUG, " - Patched Patched refreshResources 2/3");*/
                    }
                }
            }
        } else {
            System.out.println("cum pis piss piss piss");
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    private byte[] patchDummyClass(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        logger.log(Level.INFO, "Found dummy class: " + classNode.name);

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);

        return writer.toByteArray();
    }
}
