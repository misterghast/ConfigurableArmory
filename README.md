# Configurable Armory  
Configurable armory is a mod that allows for users to specify and create armor, weapons, and tools
using human-readable and easy to understand files. 

Checklist:  
- [X] Add basic tools/weapons functionality.  
    - [ ] Add Spartan Weaponry-type traits and cosmetic stuff.  
- [ ] Add armor functionality.

How do you add a weapon or tool? Simple! Just make a file in configurablearmory/items/
in your minecraft directory named whatever you want, with this as the contents:

```
{  
    id: name  
    type: tool  
    translationKey: translation.key.for.lang.file  
    material: materialName  
    tab: tabId  
    template: tool name (axe, pickaxe, shovel, sword)  
}  
```
